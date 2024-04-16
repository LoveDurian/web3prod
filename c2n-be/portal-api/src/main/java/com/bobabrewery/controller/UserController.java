package com.bobabrewery.controller;

import cn.hutool.extra.servlet.ServletUtil;
import com.alibaba.fastjson.JSONObject;
import com.bobabrewery.common.Result;
import com.bobabrewery.common.exceptin.CommonException;
import com.bobabrewery.constant.Constant;
import com.bobabrewery.enums.ReCode;
import com.bobabrewery.repo.common.domain.model.TwitterTask;
import com.bobabrewery.repo.common.domain.model.UserInfo;
import com.bobabrewery.repo.common.mapper.TwitterTaskMapper;
import com.bobabrewery.repo.common.mapper.UserInfoDao;
import com.bobabrewery.service.MailService;
import com.bobabrewery.util.MailTemplateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Email;
import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * 用户注册相关接口
 *
 * @author PailieXiangLong
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserInfoDao userInfoDao;

    @Resource
    private TwitterTaskMapper twitterTaskMapper;

    @Resource
    private MailService mailService;


    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    private static final String subject = "Boba Brewery Verification Code";

    /**
     * 发送邮箱验证码
     *
     * @param loginEmail
     * @return
     * @throws Exception
     * @apiNote 同一IP一分钟内可访问2次, 发送过的验证码5分钟时效
     */
    @GetMapping("/code")
    public Result<String> vImage(@RequestParam("loginEmail") @Email(message = "Wrong email address format!") String loginEmail) throws Exception {
        boolean contains = Arrays.stream(Constant.BAN_MAIL).collect(Collectors.toList()).contains(loginEmail.split("@")[1]);
        if (contains) {
            throw new CommonException(ReCode.NOT_SUPPORT_MAIL);
        }
        if (redisTemplate.hasKey(loginEmail) != null) {
            return Result.ok();
        }
        List<UserInfo> userList = userInfoDao.selectByEmailOnly(loginEmail);
        if (!CollectionUtils.isEmpty(userList)) {
            throw new CommonException(ReCode.MAIL_IS_EXIST);
        }
        try {
            String code = String.valueOf(new Random().nextInt(899999) + 100000);
            String template = MailTemplateUtil.loadTemplate(code);
            mailService.sendHtmlMail(subject, template, loginEmail);
            log.info("用户邮箱: {} 验证码为: {}", loginEmail, code);
            redisTemplate.opsForValue().set(loginEmail, code, Duration.ofMinutes(5));
        } catch (Exception e) {
            redisTemplate.delete(loginEmail);
            log.info("用户邮箱发送失败,清理该邮箱缓存", e);
        }

        return Result.ok();
    }

    /**
     * 注册接口
     *
     * @param request
     * @param accountId
     * @param loginEmail
     * @return
     */
    @PostMapping("/register")
    public Result register(HttpServletRequest request, @RequestParam("accountId") String accountId, @RequestParam("loginEmail") String loginEmail, @RequestParam("code") String code) {
        if (StringUtils.isBlank(accountId) || StringUtils.isBlank(loginEmail)) {
            throw new CommonException(ReCode.INVALID_PARAMETERS);
        }
        String trueCode = (String) redisTemplate.opsForValue().get(loginEmail);
        if (!code.equals(trueCode)) {
            throw new CommonException(ReCode.INVALID_CODE);
        }
        List<UserInfo> userList = userInfoDao.selectByEmail(loginEmail, accountId);
        if (!CollectionUtils.isEmpty(userList)) {
            throw new CommonException(ReCode.USER_IS_EXIST);
        }
        // 清理验证码缓存
        UserInfo userInfo = new UserInfo();
        userInfo.setLoginEmail(loginEmail);
        userInfo.setAccountId(accountId);
        userInfo.setLoginIp(ServletUtil.getClientIP(request));
        userInfo.setRegisterIp(ServletUtil.getClientIP(request));
        userInfo.setLoginTime(new Date());
        userInfoDao.insertSelective(userInfo);
        JSONObject result = new JSONObject();
        result.put("loginEmail", loginEmail);
        result.put("accountId", accountId);
        return Result.ok(result);
    }

    /**
     * 判断是否注册接口
     *
     * @param accountId 账户ID
     * @param pid       项目ID 0-平台  >0-项目ID
     * @return
     */
    @GetMapping("/is_register")
    public Result isRegister(@RequestParam("accountId") String accountId, @RequestParam Integer pid) {
        UserInfo userInfo = userInfoDao.findByAccountId(accountId);

        JSONObject result = new JSONObject();
        if (userInfo == null) {
            result.put("loginEmail", false);
            result.put("isRegister", false);
        } else {
            TwitterTask twitterTask = twitterTaskMapper.findByAccountIdAndPid(accountId, pid);
            result.put("tgId", userInfo.getTgId() != null);
            if (userInfo.getTgId() != null) {
                result.put("isRegister", true);
            } else {
                result.put("isRegister", false);
            }
            result.put("tgName", userInfo.getTgName() != null);
            result.put("loginEmail", userInfo.getLoginEmail() != null);
            result.put("userAddress", userInfo.getUserAddress() != null);
            result.put("twitterName", twitterTask != null && twitterTask.getTwitterName() != null);
            result.put("retweetLink", twitterTask != null && twitterTask.getRetweetLink() != null);
        }
        return Result.ok(result);
    }

    /**
     * 钱包绑定tg 绑定完 telegram 发送加群消息
     *
     * @param accountId
     * @param tgId
     * @param tgName
     * @return
     */
    @PostMapping("/tg_register")
    public Result registerTg(@RequestParam("accountId") String accountId, @RequestParam("tgId") String tgId, @RequestParam("tgName") String tgName) {
        UserInfo userInfo = userInfoDao.findByAccountId(accountId);
        if (userInfo != null) {
            // TODO : 判断tg重复
            if (userInfo.getTgId() == null) {
                userInfoDao.updateTgInfo(accountId, tgId, tgName);
//                try {
//                    telegramMessageService.sendRegisterPlatformSuccess(userInfo);
//                } catch (Exception e) {
//                    log.error("[Telegram平台消息发送失败]", e);
//                    return Result.ok();
//                }
                return Result.ok();
            }
        }
        throw new CommonException(ReCode.USER_IS_EXIST);
    }

    /**
     * 用户绑定地址
     *
     * @param accountId
     * @param userAddress
     * @return
     */
    @PostMapping("/address/add")
    public Result addUserAddress(@RequestParam("accountId") String accountId, @RequestParam("userAddress") String userAddress) {
        UserInfo userInfo = userInfoDao.findByAccountId(accountId);
        if (userInfo == null) {
            throw new CommonException(ReCode.USER_IS_EXIST);
        }
        userInfoDao.updateAddressInfo(accountId, userAddress);
        return Result.ok();
    }

    /**
     * 推特任务填写
     *
     * @param accountId   账户ID
     * @param retweetLink 转推链接
     * @param twitterName 推特名称
     * @param pid         项目id 0-平台 >0-项目id
     * @return
     */
    @PostMapping("/twitter/task")
    Result<String> submitRetweetTask(@RequestParam("accountId") String accountId, @RequestParam String retweetLink, @RequestParam String twitterName, @RequestParam Integer pid) {
        UserInfo byAccountId = userInfoDao.findByAccountId(accountId);
        if (byAccountId != null) {
            if (twitterName.startsWith("@")) {
                twitterName = twitterName.substring(1);
            }

            try {
                TwitterTask twitterTask = new TwitterTask();
                twitterTask.setAccountId(accountId);
                twitterTask.setPid(pid);
                twitterTask.setTwitterName(twitterName);
                twitterTask.setRetweetLink(retweetLink);
                twitterTaskMapper.create(twitterTask);
            } catch (DuplicateKeyException e) {
                throw new CommonException(ReCode.TWITTER_EXIST);
            }
        } else {
            throw new CommonException(ReCode.USER_DID_NOT_REGISTER);
        }
        return Result.ok();
    }
}

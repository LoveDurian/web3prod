package com.bobabrewery.controller;

import com.bobabrewery.common.Result;
import com.bobabrewery.common.exceptin.CommonException;
import com.bobabrewery.enums.ReCode;
import com.bobabrewery.repo.common.domain.model.UserInfo;
import com.bobabrewery.repo.common.mapper.UserInfoDao;
import com.bobabrewery.service.ReferralCodeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 推荐码功能
 *
 * @author PailieXiangLong
 */
@RestController
@RequestMapping("/referral")
public class ReferralCodeController {

    @Resource
    private ReferralCodeService referralCodeServiceImpl;

    @Resource
    private UserInfoDao userInfoDao;

    /**
     * 生成推荐Code
     *
     * @param walletAddress 邀请人钱包地址
     * @param pid           项目id
     * @return 邀请Code
     * @apiNote 若该钱包地址不存在referral_code表中 新增并返回code
     * 若存在直接返回code
     */
    @PostMapping("/code")
    public Result<String> generateReferralCode(@RequestParam String walletAddress, @RequestParam Integer pid) {
        UserInfo byAccountId = userInfoDao.findByAccountId(walletAddress);
        if (byAccountId != null) {
            String s = referralCodeServiceImpl.generateReferralCode(walletAddress, pid);
            return Result.ok(s);
        } else {
            throw new CommonException(ReCode.FAILED);
        }

    }

    /**
     * 绑定推荐
     *
     * @param referralCode 推荐码
     * @param participant  被邀请人的钱包地址
     * @return
     */
    @PostMapping("/bind")
    Result<Boolean> bindReferralCode(@RequestParam String referralCode, @RequestParam String participant) {
        Boolean isBind = referralCodeServiceImpl.bindReferralCode(referralCode, participant);
        return Result.ok(isBind);
    }


    /**
     * 统计推荐码推荐成功的人数
     *
     * @param referralCode 推荐码
     * @return
     */
    @GetMapping("/count/{referralCode}")
    Result<Long> countReferralSuccess(@PathVariable String referralCode) {
        Long count = referralCodeServiceImpl.countReferralSuccess(referralCode);
        return Result.ok(count);
    }


}

package com.bobabrewery.controller;

import com.bobabrewery.common.Result;
import com.bobabrewery.common.exceptin.CommonException;
import com.bobabrewery.enums.ReCode;
import com.bobabrewery.repo.common.domain.model.UserInfo;
import com.bobabrewery.repo.common.domain.model.UserRegisterProduct;
import com.bobabrewery.repo.common.mapper.ProductContractMapper;
import com.bobabrewery.repo.common.mapper.UserInfoDao;
import com.bobabrewery.service.IUserRegisterProductService;
import com.bobabrewery.service.ReferralCodeService;
import com.bobabrewery.service.StakeService;
import com.bobabrewery.service.impl.EncodeServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.web3j.utils.Numeric;

import javax.annotation.Resource;
import java.util.List;

/**
 * 项目注册相关接口
 *
 * @Author chenkj
 * @Date 2022/1/19 1:53 下午
 */
@Slf4j
@RestController
@RequestMapping("/register")
public class RegisterController {

    @Resource
    private IUserRegisterProductService userRegisterProductService;

    @Resource
    private ReferralCodeService referralCodeService;

    @Resource
    private EncodeServiceImpl encodeService;

    @Resource
    private UserInfoDao userInfoDao;

    @Resource
    private StakeService stakeService;

    @Resource
    private ProductContractMapper productContractMapper;

    /**
     * 用户注册接口项目
     *
     * @param accountId    钱包地址
     * @param productId    项目ID
     * @param referralCode 推荐码 可选
     * @return 签名后的数据
     */
    @PostMapping("/user_register")
    public Result<String> userRegister(@RequestParam("accountId") String accountId,
                                       @RequestParam("productId") Integer productId, String referralCode) {
        UserInfo userInfo = userInfoDao.findByAccountId(accountId);
        if (userInfo != null) {
            UserRegisterProduct userRegisterProduct = new UserRegisterProduct();
            userRegisterProduct.setProductId(productId);
            userRegisterProduct.setAccountId(accountId);
            boolean b = this.userRegisterProductService.saveOrUpdate(userRegisterProduct);
            if (b) {
                if (StringUtils.isNotBlank(referralCode)) {
                    referralCodeService.referralSuccess(referralCode, accountId);
                }
                String saleContractAddressById = productContractMapper.getSaleContractAddressById(productId);
                String hex = Numeric.prependHexPrefix((Numeric.cleanHexPrefix(accountId).concat(Numeric.cleanHexPrefix(saleContractAddressById))).toLowerCase());
                return Result.ok(encodeService.sign(hex));
            } else {
                throw new CommonException(ReCode.FAILED);
            }
        } else {
            throw new CommonException(ReCode.USER_DID_NOT_REGISTER);
        }
    }

    /**
     * 用户注册接口项目
     *
     * @param accountId 钱包地址
     * @return
     */
    @GetMapping("/can_register")
    public Result<String> canRegister(@RequestParam("accountId") String accountId) {
        if (StringUtils.isBlank(accountId)) {
            throw new CommonException(ReCode.INVALID_PARAMETERS);
        }
        boolean stake = false;
        try {
            stake = stakeService.checkStake(accountId);
        } catch (Exception e) {
            log.info("用户注册项目接口检查合约调用失败", e);
            throw new CommonException(ReCode.FAILED);
        }
        if (!stake) {
            throw new CommonException(ReCode.FAILED);
        }
        List<UserRegisterProduct> list = this.userRegisterProductService.selectByAccountId(accountId);
        if (!CollectionUtils.isEmpty(list)) {
            throw new CommonException(ReCode.USER_ALREADY_REGISTERED);
        }
        return Result.ok();
    }
}

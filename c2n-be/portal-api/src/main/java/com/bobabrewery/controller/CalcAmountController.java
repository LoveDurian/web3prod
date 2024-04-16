package com.bobabrewery.controller;

import com.alibaba.fastjson.JSONObject;
import com.bobabrewery.common.Result;
import com.bobabrewery.common.exceptin.CommonException;
import com.bobabrewery.enums.ReCode;
import com.bobabrewery.repo.common.domain.model.UserRegisterProduct;
import com.bobabrewery.service.IUserRegisterProductService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * 用户与项目相关
 *
 * @author orange
 */
@Slf4j
@RestController
@RequestMapping("/amount")
public class CalcAmountController {
    @Autowired
    private IUserRegisterProductService userRegisterProductService;


    public static final BigDecimal e18 = new BigDecimal(1000000000000000000L);

    /**
     * 计算用户的amount
     *
     * @param accountId 用户钱包
     * @param productId 项目ID
     * @return
     */
    @PostMapping("/calc")
    public Result calcAmount(@RequestParam("accountId") String accountId,
                             @RequestParam("productId") Integer productId) {
        UserRegisterProduct product = userRegisterProductService.queryUserAllocations(accountId, productId);
        if (product != null) {
            JSONObject jsonObject = new JSONObject();
            if (StringUtils.isBlank(product.getWinAmount())) {
                product.setWinAmount("0");
            }
            if (new BigDecimal(product.getWinAmount()).compareTo(new BigDecimal(10)) < 0) {
                product.setWinAmount("10");
            }
            BigDecimal d10 = new BigDecimal(10).multiply(e18);
            if (new BigDecimal(product.getWinAmount()).compareTo(d10) < 0) {
                product.setWinAmount(d10.toPlainString());
            }
            jsonObject.put("amount", product.getWinAmount());
            return Result.ok(jsonObject);
        } else {
            throw new CommonException(ReCode.DATA_NOT_FOUND);
        }


    }

    /**
     * 用户注册接口项目
     *
     * @param accountId     用户钱包
     * @param productId     项目ID
     * @param stakingAmount 质押金额
     * @return
     */
    @PostMapping("/register/add")
    public Result userRegister(@RequestParam("accountId") String accountId,
                               @RequestParam("productId") Integer productId,
                               @RequestParam("stakeAmount") String stakingAmount) {
        if (StringUtils.isBlank(accountId) || productId == null || productId < 1 || StringUtils.isBlank(stakingAmount)) {
            throw new CommonException(ReCode.INVALID_PARAMETERS);
        }
        UserRegisterProduct userRegisterProduct = new UserRegisterProduct();
        userRegisterProduct.setProductId(productId);
        userRegisterProduct.setAccountId(accountId);
        BigDecimal stakingAmountDec = new BigDecimal(stakingAmount);
        userRegisterProduct.setStakingAmount(stakingAmountDec.toString());
        this.userRegisterProductService.updateByEntity(userRegisterProduct);
        return Result.ok(ReCode.SUCCESS);
    }
}

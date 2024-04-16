package com.bobabrewery.controller;

import com.bobabrewery.common.Result;
import com.bobabrewery.constant.Constant;
import com.bobabrewery.domain.resp.AprResult;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * 获取APR的值
 */
@RestController
@RequestMapping("/apr")
public class AprController {

    @Resource
    private RedisTemplate<String, BigDecimal> redisTemplate;

    /**
     * BSC APR
     *
     * @return
     */
    @GetMapping("/bsc")
    public Result<AprResult> lp() {
        BigDecimal string = redisTemplate.opsForValue().get(Constant.BSC_APR);
        if (string != null) {
            string = BigDecimal.ZERO;
        }
        BigDecimal bsclpt = redisTemplate.opsForValue().get(Constant.BSC_LPT);
        if (bsclpt == null) {
            bsclpt = BigDecimal.ZERO;
        }
        AprResult result = new AprResult();
        result.setApr(string);
        result.setPriceInLP(bsclpt);
        return Result.ok(result);
    }

    /**
     * Boba APR
     *
     * @return
     */
    @GetMapping("/boba")
    public Result<AprResult> boba() {
        BigDecimal string = redisTemplate.opsForValue().get(Constant.BOBA_APR);
        if (string == null) {
            string = BigDecimal.ZERO;
        }
        BigDecimal bobalpt = redisTemplate.opsForValue().get(Constant.BOBA_LPT);
        if (bobalpt == null) {
            bobalpt = BigDecimal.ZERO;
        }
        AprResult result = new AprResult();
        result.setApr(string);
        result.setPriceInLP(bobalpt);
        return Result.ok(result);
    }

    @GetMapping("/bre_price")
    public Result<String> brePrice() {
        BigDecimal string = redisTemplate.opsForValue().get(Constant.BRE_PRICE);
        return Result.ok(string.toPlainString());
    }
}

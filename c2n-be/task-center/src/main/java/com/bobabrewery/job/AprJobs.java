package com.bobabrewery.job;

import com.bobabrewery.service.AprService;
import com.bobabrewery.service.CurrentPriceService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Slf4j
@Component
public class AprJobs {

    @Resource
    private AprService aprServiceImpl;

    @Resource
    private CurrentPriceService currentPriceServiceImpl;

    

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public static final String BSC_APR = "bsc_apr";

    public static final String BOBA_APR = "boba_apr";

    public static final String BSC_LPT = "bsc_lpt";

    public static final String BOBA_LPT = "boba_lpt";

    public static final String BRE_PRICE = "bre_price";

    @Scheduled(cron = "0 0/1 * * * ?")
    void loadLPApr() throws Exception {
        //BSC   APR = 3.15*BRE价格/ (质押的LP总量*LPT价格) * 3600*24*365
        //Boba   APR = 0.35*BRE价格/ (质押的LP总量*LPT价格) * 3600*24*365

        // bre price
        BigDecimal breweryPrice = currentPriceServiceImpl.getPrice("boba-brewery");
        redisTemplate.opsForValue().set(BRE_PRICE, breweryPrice);

        BigDecimal lpTokenBSC = aprServiceImpl.priceInLPTokenBSC(breweryPrice);
        redisTemplate.opsForValue().set(BSC_LPT, lpTokenBSC);

        BigDecimal lpTokenBoba = aprServiceImpl.priceInLPTokenBoba(breweryPrice);
        redisTemplate.opsForValue().set(BOBA_LPT, lpTokenBoba);

        log.info("price: BSC LP:{} ,Boba LP:{}", lpTokenBSC, lpTokenBoba);
        // apr = 0
        redisTemplate.opsForValue().set(BSC_APR, BigDecimal.ZERO);
        redisTemplate.opsForValue().set(BOBA_APR, BigDecimal.ZERO);
    }
}

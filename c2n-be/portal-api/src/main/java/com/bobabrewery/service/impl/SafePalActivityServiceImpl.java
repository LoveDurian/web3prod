package com.bobabrewery.service.impl;

import com.bobabrewery.config.AsyncConfig;
import com.bobabrewery.repo.common.domain.model.WhoStakedLog;
import com.bobabrewery.repo.common.mapper.WhoStakedLogMapper;
import com.bobabrewery.service.SafePalActivityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class SafePalActivityServiceImpl implements SafePalActivityService {

    @Resource
    private WhoStakedLogMapper mapper;

    @Override
    @Async(AsyncConfig.POOL_NAME)
    public void logAccountId(String accountId, String contactAddress, String ip) {
        int count = mapper.countByWalletAddressAndContractAddress(accountId, contactAddress);
        if (count > 0) {
            log.info("用户:{}已经参加", accountId);
        } else {
            WhoStakedLog whoStakedLog = new WhoStakedLog();
            whoStakedLog.setContractAddress(contactAddress);
            whoStakedLog.setWalletAddress(accountId);
            whoStakedLog.setIp(ip);
            int i = mapper.insertSelective(whoStakedLog);
            log.info("用户:{},参加合约:{},{}", accountId, contactAddress, i > 0 ? "成功" : "失败");
        }
    }
}

package com.bobabrewery.listener;

import com.bobabrewery.job.MetaDerbyJobs;
import com.bobabrewery.partner.constant.MetaDerbyConstant;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;

/**
 * @author PailieXiangLong
 */
@Slf4j
@Component
public class MetaDerbyListener implements MessageListener {

    @Resource
    private MetaDerbyJobs metaDerbyJobs;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void onMessage(@NotNull Message message, byte[] pattern) {
        Boolean aBoolean = redisTemplate.hasKey(MetaDerbyConstant.REFRESH_INFORMATION);
        // 锁存在时 不执行
        if (Boolean.TRUE.equals(aBoolean)) {
            log.info("MetaDerby is running: locking");
        } else {
            try {
                // 加锁
                redisTemplate.opsForValue().set(MetaDerbyConstant.REFRESH_INFORMATION, true);
                metaDerbyJobs.allHorses();
            } catch (ExecutionException | InterruptedException e) {
                log.error("MetaDerby", e);
            } finally {
                // 释放锁
                redisTemplate.delete(MetaDerbyConstant.REFRESH_INFORMATION);
                log.info("MetaDerby run done:  unlocking");
            }

        }
    }
}

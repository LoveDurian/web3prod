package com.bobabrewery.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author PailieXiangLong
 */
@Configuration
@EnableAsync
public class AsyncConfig {
    private static final int CORE_POOL_SIZE = 4;
    private static final int MAX_POOL_SIZE = 8;
    private static final int QUEUE_SIZE = 600;
    private static final int KEEP_ALIVE = 60;
    private static final String THREAD_NAME_PREFIX = "asyncPool-";

    public static final String POOL_NAME = "asyncPool";

    @Bean(POOL_NAME)
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor poolExecutor = new ThreadPoolTaskExecutor();
        // 核心线程数
        poolExecutor.setCorePoolSize(CORE_POOL_SIZE);
        // 最大线程数
        poolExecutor.setMaxPoolSize(MAX_POOL_SIZE);
        // 队列大小
        poolExecutor.setQueueCapacity(QUEUE_SIZE);
        // 线程最大空闲时间
        poolExecutor.setKeepAliveSeconds(KEEP_ALIVE);
        // 拒绝策略
        poolExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 线程名称前缀
        poolExecutor.setThreadNamePrefix(THREAD_NAME_PREFIX);
        return poolExecutor;
    }
}

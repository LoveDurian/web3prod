package com.bobabrewery.common.logging.appender;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.LoggingEventVO;
import ch.qos.logback.core.AppenderBase;
import cn.hutool.core.lang.Assert;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.StringUtils;

/**
 * @author PailieXiangLong
 */
public class RedisAppender extends AppenderBase<ILoggingEvent> {

    /**
     * redisKey template
     */
    private static final String REDIS_APPENDER_TEMPLATE = "redis_appender_%s";

    /**
     * redisKey
     */
    private String redisKey;
    /**
     * 本项目名称 默认 spring.application.name
     */
    private String project;
    /**
     * 默认 spring.redis.host
     */
    private String hostName;
    /**
     * 默认 spring.redis.port
     */
    private int port;
    /**
     * redis password 默认 spring.redis.password
     */
    private String password;
    /**
     * redis database 默认 spring.redis.database
     */
    private int database;

    /**
     * redisTemplate
     */
    private RedisTemplate<String, LoggingEventVO> redisTemplate;


    @Override
    protected void append(ILoggingEvent e) {
        LoggingEventVO build = LoggingEventVO.build(e);
        redisTemplate.opsForList().leftPush(redisKey, build);
    }

    @Override
    public void start() {
        if (!this.isStarted()) {
            if (StringUtils.hasLength(this.project)) {
                redisKey = String.format(REDIS_APPENDER_TEMPLATE, project);
            } else {
                this.addError("projectName not null, try adding properties 'spring.application.name'");
            }

            Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
            objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.EXISTING_PROPERTY);
            serializer.setObjectMapper(objectMapper);

            RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(this.hostName, this.port);
            if (this.database >= 0) {
                configuration.setDatabase(database);
            }
            if (StringUtils.hasLength(password)) {
                configuration.setPassword(password);
            }
            RedisTemplate<String, LoggingEventVO> redisTemplate = new RedisTemplate<String, LoggingEventVO>();
            LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(configuration);
            lettuceConnectionFactory.afterPropertiesSet();
            redisTemplate.setConnectionFactory(lettuceConnectionFactory);
            redisTemplate.setKeySerializer(RedisSerializer.string());
            redisTemplate.setValueSerializer(serializer);
            redisTemplate.afterPropertiesSet();
            this.redisTemplate = redisTemplate;
            Assert.notNull(this.redisTemplate, "redis null");
        }
        super.start();
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getDatabase() {
        return database;
    }

    public void setDatabase(int database) {
        this.database = database;
    }
}

package com.bobabrewery.common.interceptor;

import com.bobabrewery.common.annotation.Limit;
import com.bobabrewery.common.enums.ReCode;
import com.bobabrewery.common.exceptin.CommonException;
import com.bobabrewery.common.util.IpUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * 接口限流
 *
 * @author PailieXiangLong
 */
public interface LimitInterceptor extends HandlerInterceptor {

    String LIMIT_KEY_TEMPLATE = "limit_%s_%d";

    /**
     * 加载Redis
     *
     * @return
     */
    RedisTemplate<String, Integer> loadRedis();

    /**
     * 检查API限制
     *
     * @param limit
     * @param limitKey
     * @return
     */
    default boolean checkLimit(Limit limit, String limitKey) {
        RedisTemplate<String, Integer> redisTemplate = loadRedis();
        int max = limit.max();
        int time = limit.time();
        TimeUnit timeUnit = limit.timeUnit();
        ReCode msg = limit.msg();
        Integer count = redisTemplate.opsForValue().get(limitKey);
        if (count != null) {
            if (count < max) {
                Long expire = redisTemplate.getExpire(limitKey);
                if (expire != null && expire <= 0) {
                    redisTemplate.opsForValue().set(limitKey, 1, time, timeUnit);
                } else {
                    redisTemplate.opsForValue().set(limitKey, ++count, time, timeUnit);
                }
            } else {
                throw new CommonException(msg);
            }
        } else {
            redisTemplate.opsForValue().set(limitKey, 1, time, timeUnit);
        }
        return true;
    }

    /**
     * 拦截器
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @apiNote 根据用户的IP和请求路径来防御
     */
    @Override
    default boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            Limit limit = method.getAnnotation(Limit.class);
            if (limit == null) {
                return true;
            } else {
                String limitKey = String.format(LIMIT_KEY_TEMPLATE, request.getRequestURI(),
                        IpUtils.ipToLong(IpUtils.getIpAdrress(request)));
                return checkLimit(limit, limitKey);
            }
        } else {
            return false;
        }

    }


}

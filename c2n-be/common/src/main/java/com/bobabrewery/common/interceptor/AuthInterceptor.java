package com.bobabrewery.common.interceptor;

import cn.hutool.json.JSONException;
import com.bobabrewery.common.annotation.Auth;
import com.bobabrewery.common.annotation.NoAuth;
import com.bobabrewery.common.enums.ReCode;
import com.bobabrewery.common.exceptin.CommonException;
import com.bobabrewery.common.util.JWTUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author PailieXiangLong
 * 认证拦截
 */
public interface AuthInterceptor extends HandlerInterceptor {

    /**
     * 请求头中的Token名称
     */
    String TOKEN_NAME = "token";

    /**
     * 检查token判断是否放行
     *
     * @param userId
     * @param roleId
     * @return
     */
    boolean checkPermission(Integer userId, Integer roleId, String permission);

    @Override
    default boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            // 存在NoAuth注解的无需认证
            NoAuth annotation = method.getAnnotation(NoAuth.class);
            if (annotation != null) {
                return true;
            }
            String token = request.getHeader(TOKEN_NAME);
            if (StringUtils.isNotBlank(token)) {
                // TODO: redis查看token是否存在 key: md5hash
                // 验证token的签名
                try {
                    if (JWTUtils.verify(token)) {
                        // 是否过期
                        if (JWTUtils.expired(token)) {
                            // 权限认证
                            Auth auth = method.getAnnotation(Auth.class);
                            if (auth != null) {
                                Integer roleId = JWTUtils.getRoleId(token);
                                Integer userId = JWTUtils.getUserId(token);
                                return checkPermission(userId, roleId, auth.value());
                            } else {
                                return true;
                            }
                        }
                    } else {
                        throw new CommonException(ReCode.TOKEN_EXPIRED);
                    }
                } catch (JSONException e) {
                    throw new CommonException(ReCode.INVALID_TOKEN);
                }
            }
            throw new CommonException(ReCode.INVALID_TOKEN);
        } else {
            return false;
        }
    }
}

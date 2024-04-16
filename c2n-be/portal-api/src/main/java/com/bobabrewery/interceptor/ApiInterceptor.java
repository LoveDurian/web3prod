package com.bobabrewery.interceptor;

import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.bobabrewery.common.exceptin.CommonException;
import com.bobabrewery.enums.ReCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 针对API的拦截器
 *
 * @author orange
 */
@Slf4j
@Component
public class ApiInterceptor implements HandlerInterceptor {

    private final static String TOKEN = "8d5bb6ebcd4b432fbefb6723a2e8d360";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        String walletAddress = map.get("walletAddress");
        String token = request.getHeader("token");
        log.info("[API request] form:{},path:{},wallet:{},token:{}", ServletUtil.getClientIP(request), request.getRequestURI(), walletAddress, token);
        if (token != null && walletAddress != null) {
            String encode = DigestUtil.md5Hex(walletAddress + TOKEN);
            if (token.equals(encode)) {
                return true;
            }
        }
        throw new CommonException(ReCode.INVALID_TOKEN);
    }
}

package com.sky.interceptor;

import com.sky.constant.JwtClaimsConstant;
import com.sky.context.BaseContext;
import com.sky.properties.JwtProperties;
import com.sky.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * JWT token validation interceptor
 */
@Component
@Slf4j
public class JwtTokenUserInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtProperties jwtProperties;

    /**
     * Validate JWT
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Check if the intercepted handler is a Controller method or another resource
        if (!(handler instanceof HandlerMethod)) {
            // If the intercepted handler is not a dynamic method, allow the request to pass
            return true;
        }

        // 1. Get the token from the request header
        String token = request.getHeader(jwtProperties.getUserTokenName());

        // 2. Validate the token
        try {
            log.info("JWT validation: {}", token);
            Claims claims = JwtUtil.parseJWT(jwtProperties.getUserSecretKey(), token);
            Long userId = Long.valueOf(claims.get(JwtClaimsConstant.USER_ID).toString());
            log.info("Current user id: {}", userId);
            BaseContext.setCurrentId(userId); // Save employee id in current thread
            // 3. If valid, allow the request to proceed
            return true;
        } catch (Exception ex) {
            // 4. If invalid, respond with 401 status code
            response.setStatus(401);
            return false;
        }
    }
}

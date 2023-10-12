package com.example.apisecurity.interceptor;

import com.example.apisecurity.exception.NoBearerTokenError;
import com.example.apisecurity.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class AuthorizationInterceptor implements HandlerInterceptor {
    private final UserDetailsService userDetailsService;
    private final UserService userService;
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        String handlerString = request.getHeader("Authorization");
        if(handlerString == null ||
        !handlerString.startsWith("Bearer")){
            throw new NoBearerTokenError();
        }
        request.setAttribute("user", userService.getUserFromToken(handlerString.substring(7)));
        return true;
    }
}

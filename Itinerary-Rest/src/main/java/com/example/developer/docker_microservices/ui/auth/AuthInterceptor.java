package com.example.developer.docker_microservices.ui.auth;

import com.example.developer.docker_microservices.ui.exception.PermissionException;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AuthInterceptor implements HandlerInterceptor {
    private static final String TOKEN = "token";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!handler.getClass().isAssignableFrom(HandlerMethod.class)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Class<?> clazz = handlerMethod.getMethod().getDeclaringClass();
        if (!clazz.isAnnotationPresent(Auth.class)) {
            return true;
        }
        if (!TOKEN.equalsIgnoreCase(request.getHeader("auth_token"))) {
            throw new PermissionException("Permission denied");
        }
        return true;
    }
}

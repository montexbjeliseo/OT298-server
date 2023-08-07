package com.alkemy.ong.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Autowired
    MessageSource messageSource;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.addHeader("access_denied_reason", "authentication_required");
        response.sendError(HttpStatus.FORBIDDEN.value(), messageSource.getMessage("access.denied", null, Locale.US));
    }
}

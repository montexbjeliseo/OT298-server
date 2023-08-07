package com.alkemy.ong.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Autowired
    MessageSource messageSource;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.addHeader("access_denied_reason", "authentication_required");
        response.sendError(HttpStatus.FORBIDDEN.value(), messageSource.getMessage("access.denied", null, Locale.US));
    }
}

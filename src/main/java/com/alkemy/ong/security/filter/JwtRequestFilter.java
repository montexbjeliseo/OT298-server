package com.alkemy.ong.security.filter;


import com.alkemy.ong.security.service.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer ";
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        final String authorizationHeader = httpServletRequest.getHeader(AUTHORIZATION);

        String username = null;
        String jwt = null;

        if(authorizationHeader != null && authorizationHeader.startsWith(BEARER)){
            jwt = authorizationHeader.substring(7);
            username = jwtUtils.extractUsername(jwt);
        }

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if(jwtUtils.validateToken(jwt, userDetails)){
                UsernamePasswordAuthenticationToken authReq =
                        new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
                authReq.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(authReq);
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }

}

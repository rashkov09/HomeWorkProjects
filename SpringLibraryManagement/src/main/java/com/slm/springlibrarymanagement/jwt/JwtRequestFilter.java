package com.slm.springlibrarymanagement.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Objects;

import static ch.qos.logback.core.CoreConstants.EMPTY_STRING;

@Component

public class JwtRequestFilter extends OncePerRequestFilter {
    private final JwtTokenUtil tokenUtil;
    private final JwtUserDetailsService userDetailsService;

    @Autowired
    public JwtRequestFilter(JwtTokenUtil tokenUtil, JwtUserDetailsService userDetailsService) {
        this.tokenUtil = tokenUtil;
        this.userDetailsService = userDetailsService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Enumeration<String> headers = request.getHeaders("Authorization");
        String tokenHeader = headers.hasMoreElements() ? headers.nextElement() : EMPTY_STRING;


        if (Objects.nonNull(tokenHeader) && tokenHeader.startsWith("Bearer ")) {
            String jwtToken = tokenHeader.substring(7);
            String username = tokenUtil.getUsernameFromToken(jwtToken);

            if (Objects.nonNull(username) && Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (tokenUtil.validateToken(jwtToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,
                            null, userDetails.getAuthorities());
                    authenticationToken.setDetails(request);

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}

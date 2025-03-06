package com.hcodes.eventus.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author mavis
 */


public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JWTGenerator jwtGenerator;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
                String token = getJWTFromRequest(request);
                if (StringUtils.hasText(token) && jwtGenerator.validateToken(token)) {
                    String username = jwtGenerator.getUsernameFromToken(token); 
                    UserDetails userDetails = customUserDetailService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());   
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }

                filterChain.doFilter(request, response);
    }

    private String getJWTFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }


}

package com.example.todo.security;

import com.example.todo.service.impl.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final UserDetailsServiceImpl userDetailsService;

    public JwtAuthFilter(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = authorizationHeader.substring("Bearer ".length());
            String username = JwtUtil.getUsername(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities()));
        } catch (Exception exception) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(exception.getMessage());
            response.getWriter().flush();
            return;
        }
        filterChain.doFilter(request, response);
    }
}

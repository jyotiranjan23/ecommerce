package com.ecommerce.cart.filter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ecommerce.cart.util.JWTUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    @Autowired
    private JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain){
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String userName = null;
        if(authHeader != null && authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7);
            userName = jwtUtil.extractUserName(token);
        }
        if(userName != null){
            UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(userName, null, List.of());

            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        try{
            filterChain.doFilter(request, response);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
         
    }

}

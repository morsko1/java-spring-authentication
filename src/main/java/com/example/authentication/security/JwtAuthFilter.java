package com.example.authentication.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import com.example.authentication.service.UserDetailsServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
  @Value("${application.security.jwtCookieName}")
  private String jwtCookieName;

  @Autowired
  private JwtService jwtService;

  @Autowired
  private UserDetailsServiceImpl userDetailsServiceImpl;

  @Override
  protected void doFilterInternal(
    HttpServletRequest request,
    HttpServletResponse response,
    FilterChain filterChain
  ) throws ServletException, IOException {
    if (request.getServletPath().startsWith("/api/auth/")) {
      filterChain.doFilter(request, response);
      return;
    }

    try {
      String token = getToken(request);

      if (token != null) {
        String username = jwtService.extractUsername(token);
        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(username);

        if (jwtService.isTokenValid(token, userDetails)) {
          UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
            userDetails,
            null,
            userDetails.getAuthorities()
          );

          authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

          SecurityContextHolder.getContext().setAuthentication(authentication);
        }
      }
    } catch (Exception e) {
      System.out.println("Cannot set user authentication: " + e.getMessage());
      e.printStackTrace();
    }

    filterChain.doFilter(request, response);
  }

  private String getToken(HttpServletRequest request) {
    Cookie cookie = WebUtils.getCookie(request, jwtCookieName);
    return cookie != null ? cookie.getValue() : null;
  }
}

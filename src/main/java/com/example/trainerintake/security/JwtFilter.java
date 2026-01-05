package com.example.trainerintake.security;

import com.example.trainerintake.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtFilter.class);

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Autowired
    public JwtFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();
        log.info("➡️ Incoming request path: {}", path);

        // Skip JWT validation for auth endpoints and client registration
        if (path.equals("/auth/login") || path.equals("/auth/register") || path.equals("/api/clients/register")) {
            log.debug("⏭️ Skipping JWT filter for path: {}", path);
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            log.debug("🔐 Found Authorization header, token extracted");

            try {
                username = jwtService.extractUsername(token); // subject/email from JWT
                log.info("👤 Username extracted from JWT: {}", username);
            } catch (Exception e) {
                log.error("❌ Failed to extract username from token", e);
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid or expired token");
                return;
            }
        } else {
            log.warn("⚠️ No Authorization header or not Bearer type");
        }

        // If we got a username and no authentication is set yet
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            log.debug("🔎 Loading UserDetails for: {}", username);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            log.info("📦 Loaded UserDetails: {} | Authorities: {}", 
                     userDetails.getUsername(), userDetails.getAuthorities());

            boolean valid = jwtService.isTokenValid(token, userDetails);
            log.info("🔑 Token validation result for {}: {}", username, valid);

            if (valid) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
                log.info("✅ Authentication set in SecurityContext for user: {}", username);
            } else {
                log.warn("❌ Token invalid for user: {}", username);
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid or expired token");
                return; // ✅ stop here, don’t continue filterChain

            }
        } else {
            log.debug("⚠️ Username is null or authentication already set");
        }

        filterChain.doFilter(request, response);
    }
}
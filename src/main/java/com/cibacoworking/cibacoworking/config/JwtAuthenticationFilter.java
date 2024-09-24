package com.cibacoworking.cibacoworking.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.ExpiredJwtException;
import com.cibacoworking.cibacoworking.security.JwtUtil;

import java.io.IOException;
import java.util.logging.Logger;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = Logger.getLogger(JwtAuthenticationFilter.class.getName());
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final JwtConfig jwtConfig;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService, JwtConfig jwtConfig) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.jwtConfig = jwtConfig;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String token = getTokenFromRequest(request);

        if (token != null && StringUtils.hasText(token)) {
            String username;
            try {
                username = jwtUtil.extractUsername(token);
                logger.info("Token vàlid per l'usuari: " + username);
            } catch (ExpiredJwtException e) {
                logger.warning("Token expirat per l'usuari: " + e.getMessage());
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token expirat");
                return;
            } catch (Exception e) {
                logger.warning("Token invàlid: " + e.getMessage());
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token invàlid");
                return;
            }

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            if (jwtUtil.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken = 
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } else {
                logger.warning("Token no vàlid per l'usuari: " + username);
            }
        } else {
            logger.warning("Token no proporcionat");
        }

        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        final String authHeader = request.getHeader(jwtConfig.getHeader());
        if (StringUtils.hasText(authHeader) && authHeader.startsWith(jwtConfig.getPrefix())) {
            return authHeader.substring(jwtConfig.getPrefix().length()).trim();
        }
        return null;
    }
}

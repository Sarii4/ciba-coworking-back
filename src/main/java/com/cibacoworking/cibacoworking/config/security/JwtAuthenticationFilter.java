package com.cibacoworking.cibacoworking.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cibacoworking.cibacoworking.services.CustomUserDetailsService;

import java.io.IOException;
import java.util.logging.Logger;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = Logger.getLogger(JwtAuthenticationFilter.class.getName());

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, CustomUserDetailsService customUserDetailsService) {
        this.jwtUtil = jwtUtil;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, 
                                    @NonNull HttpServletResponse response, 
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        // Loguea el encabezado de autorización para verificarlo
        logger.info("Authorization header: " + request.getHeader(HttpHeaders.AUTHORIZATION));

        final String token = getTokenFromRequest(request);

        if (token != null && StringUtils.hasText(token)) {
            String username = jwtUtil.extractUsername(token);
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

            if (jwtUtil.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                logger.info("Usuario autenticado: " + username);
            } else {
                logger.warning("Token no válido para el usuario: " + username);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token JWT no válido.");
                return; // Salimos del método si el token no es válido
            }
        } else {
            logger.warning("Token no proporcionado");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Se requiere un token JWT para acceder a este recurso.");
            return; // Salimos del método si no hay token
        }

        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);  // Remueve "Bearer " para obtener el token real
        }
        return null;
    }

    @Override
    protected boolean shouldNotFilter(@SuppressWarnings("null") HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();
        // Excluye las rutas públicas que no necesitan autenticación
        return path.equals("/api/users/login") || path.equals("/api/users/register") || path.equals("/api/users/logout");
    }
}

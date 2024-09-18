// package com.cdigital.cdigital_backend.config;

// import java.io.IOException;

// import org.springframework.http.HttpHeaders;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
// import org.springframework.stereotype.Component;
// import org.springframework.util.StringUtils;
// import org.springframework.web.filter.OncePerRequestFilter;

// import com.cdigital.cdigital_backend.security.JwtUtil;
// import com.cdigital.cdigital_backend.services.CustomUserDetailsService;

// import jakarta.servlet.FilterChain;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;

// @Component
// public class JwtAuthenticationFilter extends OncePerRequestFilter {
//     private final JwtUtil jwtUtil;
//     private final CustomUserDetailsService customUserDetailsService;

//     public JwtAuthenticationFilter(JwtUtil jwtUtil, CustomUserDetailsService customUserDetailsService) {
//         this.jwtUtil = jwtUtil;
//         this.customUserDetailsService = customUserDetailsService;
//     }

//     @Override
//     protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//             throws ServletException, IOException {
//         final String token = getTokenFromRequest(request);

//         if (token != null && StringUtils.hasText(token)) {
//             String username = jwtUtil.extractUsername(token);
//             UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

//             if (jwtUtil.validateToken(token, userDetails)) {
//                 UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
//                         userDetails,
//                         null, userDetails.getAuthorities());

//                 authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                 SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//             }
//         }

//         filterChain.doFilter(request, response);
//     }

//     private String getTokenFromRequest(HttpServletRequest request) {
//         final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

//         if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
//             return authHeader.substring(7);
//         }
//         return null;
//     }
// }
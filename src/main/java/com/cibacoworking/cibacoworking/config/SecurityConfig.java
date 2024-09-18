// package com.cibacoworking.cibacoworking.config;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.http.HttpMethod;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.AuthenticationProvider;
// import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
// import org.springframework.security.config.Customizer;
// import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
// import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
// import org.springframework.web.servlet.config.annotation.CorsRegistry;
// import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// import com.cdigital.cdigital_backend.services.CustomUserDetailsService;

// @Configuration
// @EnableWebSecurity
// @EnableMethodSecurity
// public class SecurityConfig {

//     @Bean
//     public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter,
//             AuthenticationProvider authenticationProvider) throws Exception {
//         http    
//         .cors(Customizer.withDefaults())
//                 .csrf(csrf -> csrf.disable())
//                 .authorizeHttpRequests(authorize -> authorize
//                         //.requestMatchers("/api/users/**").permitAll()
//                         .requestMatchers("/api/users/login").permitAll()
//                         .requestMatchers("/api/users/register").permitAll()
//                         .requestMatchers(HttpMethod.GET, "/api/courses").permitAll()
//                         .requestMatchers(HttpMethod.POST, "/api/courses").permitAll()
//                         .requestMatchers(HttpMethod.PUT, "/api/courses/**").hasRole("ADMIN")
//                         .requestMatchers(HttpMethod.DELETE, "/api/courses/**").hasRole("ADMIN") 
//                         .anyRequest().authenticated())
//                 .sessionManagement(session -> session
//                         .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                 .authenticationProvider(authenticationProvider)
//                 .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//         return http.build();
//     }

//     @Bean
//     public PasswordEncoder passwordEncoder() {
//         return new BCryptPasswordEncoder();
//     }

//     @Bean
//     public AuthenticationProvider authenticationProvider(PasswordEncoder passwordEncoder,
//             CustomUserDetailsService customUserDetailsService) {
//         DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//         authProvider.setUserDetailsService(customUserDetailsService);
//         authProvider.setPasswordEncoder(passwordEncoder);
//         return authProvider;
//     }

//     @Bean
//     public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
//             throws Exception {
//         return authenticationConfiguration.getAuthenticationManager();
//     }

//     @Bean
//     public WebMvcConfigurer corsConfigurer() {
//         return new WebMvcConfigurer() {
//             @Override
//             public void addCorsMappings(CorsRegistry registry) {
//                 registry.addMapping("/**")
//                         .allowedOrigins("http://localhost:5173")
//                         .allowedMethods("GET", "POST", "PUT", "DELETE")
//                         .allowedHeaders("Content-Type", "accept", "Authorization")
//                         .allowCredentials(true);
//             }
//         };
//     }
// }
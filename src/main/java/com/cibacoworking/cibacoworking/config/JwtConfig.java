package com.cibacoworking.cibacoworking.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Value("${jwt.secret:defaultSecret}")
    private String secret;

    @Value("${jwt.expiration:3600000}")
    private long expiration;

    @Value("${jwt.prefix:Bearer}")
    private String prefix;

    @Value("${jwt.header:Authorization}")
    private String header;

    public String getSecret() {
        return secret;
    }

    public long getExpiration() {
        return expiration;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getHeader() {
        return header;
    }
}

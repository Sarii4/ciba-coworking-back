package com.cibacoworking.cibacoworking.config.security;

import org.springframework.security.web.firewall.StrictHttpFirewall;

public class CustomHttpFirewall extends StrictHttpFirewall {
    public CustomHttpFirewall() {
        setAllowUrlEncodedDoubleSlash(true); // Permitir dobles slashes
    }
}

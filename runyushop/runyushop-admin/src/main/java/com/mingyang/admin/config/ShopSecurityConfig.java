package com.mingyang.admin.config;

import com.mingyang.admin.entity.UmsAdmin;
import com.mingyang.admin.service.IUmsAdminService;
import com.mingyang.security.config.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author Mingyang
 */
@Configuration
public class ShopSecurityConfig extends SecurityConfig {

    @Autowired
    private IUmsAdminService umsAdminService;


    @Override
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            UmsAdmin admin = umsAdminService.getAdminByUsername(username);
            if (admin != null) {
                return admin;
            }
            return null;
        };
    }
}

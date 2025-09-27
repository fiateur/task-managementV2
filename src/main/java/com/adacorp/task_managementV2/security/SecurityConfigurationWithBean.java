package com.adacorp.task_managementV2.security;

import com.adacorp.task_managementV2.constantes.RoleConstante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// @Configuration
// @EnableWebSecurity
public class SecurityConfigurationWithBean {

    // ✅ Bean unique
    @Bean
    public PasswordEncoder passwordEncoderWithBean() {
        return new BCryptPasswordEncoder();
    }

    // ✅ Configure global auth : [Authentification En mémoire dans la JVM - Sans Avoir implémenté une Page d'accueil...]
    // @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user1").password( ( passwordEncoderWithBean().encode("123456789") )  ).roles(RoleConstante.ROLE_USER)
                .and()
                .withUser("admin1").password( passwordEncoderWithBean().encode("admin123") ).roles(RoleConstante.ROLE_ADMIN)
        ;
    }
}


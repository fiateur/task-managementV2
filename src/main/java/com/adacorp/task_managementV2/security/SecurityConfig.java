package com.adacorp.task_managementV2.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatchers;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfig(CustomUserDetailsService customUserDetailsService,PasswordEncoder passwordEncoder) {
        this.customUserDetailsService = customUserDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        // Configure AuthenticationManagerBuilder
        AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(this.passwordEncoder);
                //.passwordEncoder(passwordEncoder());

        // Get AuthenticationManager
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        httpSecurity
                .authorizeHttpRequests(
                        auth -> auth
                        // Sans être Authentifié — on accorde les droits de lecture et d'utilisation à ces ressources
                        .requestMatchers("/","/create-user-test").permitAll()
                        .requestMatchers("/index-logout").permitAll()
                        .requestMatchers("/index-login").permitAll()
                        .requestMatchers("/index-forgot-password").permitAll()
                        .requestMatchers("/index-forgot-password").permitAll()
                        .requestMatchers("/index-forgot-password-send-code").permitAll()
                        .requestMatchers("/index-forgot-password-otpValidation-code").permitAll()
                        .requestMatchers("/index-forgot-password-changePassword-After-otpValidation-code").permitAll()
                        .requestMatchers("/index-register-user").permitAll()
                        .requestMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/assets/**", "/images/**", "/assets/demo/**","/assets/img/**").permitAll()
                        // Le reste, s'authentifier
                        .anyRequest().authenticated()
                )
                //.formLogin(form -> form.disable())
                //.formLogin(AbstractHttpConfigurer::disable)
                .formLogin(
                        form -> form
                    // Url de soumission du formulaire
                    .loginProcessingUrl("/login")
                    // Page d'index ou Authentification de l'Application.
                    .loginPage("/index-login")
                    // Page d'Accueil de l'Application.
                    .defaultSuccessUrl("/dashboard")
                    // Si Authentification échouée, on retourne vers la Page index-login
                    .failureUrl("/index-login?error=true")
                    // Champ name="email" de l'input type="email" du formulaire
                    // <input class="form-control" id="inputEmail" type="email" placeholder="name@example.com" name="email" aria-label="inputEmail"/>
                    .usernameParameter("email")
                    // Champ name="password" de l'input type="password" du formulaire
                    // <input class="form-control" id="inputPassword" type="password" placeholder="Password" name="password" aria-label="inputPassword"/>
                    .passwordParameter("password")
                )
                //.logout().permitAll()
                .logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/index-logout")).permitAll()
                                .logoutSuccessUrl("/login?logout")
                )
                //.exceptionHandling(exceptionHandling -> exceptionHandling.accessDeniedPage("/access-denied"))
                .authenticationManager(authenticationManager);

        return httpSecurity.getOrBuild();

    }

    /*
        @Bean
        public PasswordEncoder passwordEncoder() {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            return bCryptPasswordEncoder;
        }
    */


}

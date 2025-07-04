package com.radhaa.QUIZAPPLICATIONBackend.Configuration;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig
{
    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(e->e.disable())
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(req->
                        req.requestMatchers(
                                        "/api/addNewUser",
                                        "/api/validate",
                                        "/HomePage/**",
                                        "/Authentication/**",
                                        "/quiz/**",
                                        "/Assets/**",
//                                        "/validateAns/**",
//                                        "/product/v1/**",
//                                        "/quizPurchase/**",
                                        "/Check/**",
//                                        "/file/**",
                                        "/score/**"
//                                        "/search/**"
                                        ).permitAll()
                                .anyRequest().authenticated())
                .formLogin(form->
                        form.loginPage("/Authentication/login-signup.html?mode=Login")
                                .loginProcessingUrl("/doLogin")
                                .defaultSuccessUrl("/HomePage/index.html",true)
                                .failureUrl("/Authentication/login-signup.html?mode=signUp?error=true"))
                //to remove default signin form spring security provide
                .exceptionHandling(exception -> exception
                                .authenticationEntryPoint((request, response, authException) -> {
                                    // THIS IS IMPORTANT: redirect if accessed via browser
                                    if (request.getHeader("Accept") != null && request.getHeader("Accept").contains("text/html")) {
                                        response.sendRedirect("/authentication/auth.html?mode=login");
                                    } else {
                                        // If it's an API (e.g., Accept: application/json), don't redirect
                                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                                        response.setHeader("WWW-Authenticate", ""); // prevent popup
                                        response.setContentType("application/json");
                                        response.getWriter().write("{\"error\":\"Unauthorized\"}");
                                    }
                                })
                        )
                .logout(logoutForm->
                {
                    logoutForm.logoutUrl("/logout");
                    logoutForm.logoutSuccessUrl("/Authentication/login-signup.html?mode=login");

                })
                .httpBasic(Customizer.withDefaults());

            return http.build();

    }

    @Bean
    UrlBasedCorsConfigurationSource corsConfigurationSource()
    {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://127.0.0.1:5500"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST"));
        configuration.setAllowedHeaders(Arrays.asList("*")); // 🔥 Add this
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

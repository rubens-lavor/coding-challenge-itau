package com.filmreview.security;

import com.filmreview.security.JWTValidationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
public class JWTConfiguration extends WebSecurityConfigurerAdapter {
//    private final UserDetailsService userDetailsService;
//    // private final PasswordEncoder passwordEncoder;

//    public JWTConfiguration(UserDetailsService userDetailsService/*, PasswordEncoder passwordEncoder*/) {
//        this.userDetailsService = userDetailsService;
//        // this.passwordEncoder = passwordEncoder;
//    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, "/login-account").permitAll()
                .antMatchers(HttpMethod.POST, "/create-account").permitAll()
                .anyRequest().authenticated()
                .and()
                //.addFilter(new JWTAuthenticationFilter(authenticationManager())) // para autenticar, no fim das contas esse deve ser o unico filtro nessa api
                .addFilter(new JWTValidationFilter(authenticationManager())) // para validar.. acredito que deva passar apenas esse ultimo filtro na film api
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // para não guardar a sessão do usuário no servidor
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }
}

package com.novi.config;

import com.novi.services.JwtRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig {
    private final DataSource dataSource;
    public SecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    //Deze methode regelt de password encryption
    @Bean
    public PasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder();
    }

    //Deze methode regelt de authorisatie (dat de juiste rol of authority toegang krijgt tot een endpoint)
    @Bean
    protected SecurityFilterChain filter (HttpSecurity http) throws Exception
    {
        http
                .csrf().disable()
                .httpBasic().disable()
                .cors().and()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET, "/info").hasRole("USER")
                .requestMatchers("/users/**").hasAnyRole("ADMIN", "USER")
                .requestMatchers("/admins").hasAuthority("ROLE_ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/users/{id}").hasRole("ADMIN")
                .requestMatchers("/authenticate").permitAll()

                .anyRequest().denyAll()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                http.addFilterBefore(jwtRequestFilter),
        UsernamePasswordAuthenticationFilter.class);
            return http.build();
    }

    //Deze methode regelt de authenticatie (je zegt wie je zegt dat je bent)
    @Bean
    public AuthenticationManager authManager (HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery(
                        "SELECT username, password, enabled"
                        " FROM users" +
                        " WHERE username=?")
                .authoritiesByUserNameQuery(
                        "SELECT username, authority" +
                        " FROM authorities " +
                        " WHERE username=?");
        return authenticationManagerBuilder.build();
    }
}

package practice.mayank.ecommerce.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import practice.mayank.ecommerce.security.entity.PermissionEnum;
import practice.mayank.ecommerce.security.entity.RoleEnum;
import practice.mayank.ecommerce.security.CustomAccessDeniedHandler;
import practice.mayank.ecommerce.security.CustomAuthenticationEntryPoint;
import practice.mayank.ecommerce.security.JwtAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers("/public/**", "/error", "/documentation/**").permitAll()
                                .requestMatchers("/user/**").hasRole(RoleEnum.USER.name())
                                .requestMatchers("/management/admin/**").hasRole(RoleEnum.ADMIN.name())
                                .requestMatchers("/management/employee/**")
                                .hasAnyRole(RoleEnum.ADMIN.name(), RoleEnum.EMPLOYEE.name())
                                .requestMatchers("/management-workspace/**")
                                .hasAnyRole(RoleEnum.EMPLOYEE.name(), RoleEnum.ADMIN.name())
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(ex -> {
                    ex.authenticationEntryPoint(new CustomAuthenticationEntryPoint());
                    ex.accessDeniedHandler(new CustomAccessDeniedHandler());
                })
                .build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

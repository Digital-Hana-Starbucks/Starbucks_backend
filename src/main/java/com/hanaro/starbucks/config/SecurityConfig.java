package com.hanaro.starbucks.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtUtil jwtConfig;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf((auth) -> auth.disable())
                .cors((cors) -> cors.configurationSource(corsConfigurationSource()))

//                .authorizeHttpRequests((auth) -> auth
//                        // 로그인과 회원가입은 모든 사용자에게 허용한다.
//                        .requestMatchers(
//                                new AntPathRequestMatcher("/login"),
//                                new AntPathRequestMatcher("/signup")
//                        ).permitAll() // 권한이 있든 말든 모두 접근 가능
//                        // admin일 경우에만 /admin에 대한 요청에서 접근을 허용한다.
//                        .requestMatchers("/admin").hasRole("ADMIN")
//                        // 그 외 모든 요청은 인증된 사용자에게만 허용한다.
//                        .anyRequest().authenticated()
//                )
//                .formLogin((formLogin) -> formLogin
//                        .loginProcessingUrl("/login")
//                        .successHandler(((request, response, authentication) -> {
//                            System.out.println("로그인 성공했습니다.");
//                            response.sendRedirect("/");
//                        }))
//                        .permitAll()
//                );

                .authorizeHttpRequests( (auth) -> auth
                        .requestMatchers( new AntPathRequestMatcher("/**") )
                        .permitAll())

                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS // 세션에 저장하지 않겠다. 기본이 세션에 저장하는 것
                        ))
                .addFilterBefore(new JwtAuthenticationFilter(jwtConfig),
                UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedHeaders(Collections.singletonList("*")); // 허용할 HTTP header
        config.setAllowedMethods(Collections.singletonList("*")); // 허용할 HTTP method
        config.setAllowedOriginPatterns(Collections.singletonList("http://localhost:5173")); // 허용할 출처
        config.setAllowCredentials(true); // 쿠키 인증 요청 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}

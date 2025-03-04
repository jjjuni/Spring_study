package umc.spring.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import umc.spring.config.security.OAuth2.CustomOAuth2UserService;
import umc.spring.config.security.OAuth2.filter.ExceptionHandlerFilter;
import umc.spring.config.security.OAuth2.handler.OAuth2FailureHandler;
import umc.spring.config.security.OAuth2.handler.OAuth2SuccessHandler;
import umc.spring.domain.token.JwtUtil;
import umc.spring.config.security.OAuth2.filter.JwtAuthenticationFilter;
import umc.spring.domain.token.service.JwtService;
import umc.spring.domain.user.repository.UserRepository;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    public static final String[] PERMITTED_URI = {"/oauth2/**", "/api/auth/**", "/login", "/login/oauth2/**", "/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html", "/swagger-resources/**"};

    private final JwtService jwtService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final OAuth2FailureHandler oAuth2FailureHandler;

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of(
                "http://localhost:3000",
                "http://localhost:8080"
        ));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setExposedHeaders(List.of("Authorization"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
//                필터 사용하여 구현하면 아래와 같이 구현 가능! 컨트롤러에서 인증을 하는 것이 아닌 필터 단에서 모든 인증을 마치고 컨트롤러로 넘겨주는 방식. 단 이 방식을 사용 할 시 exception에 대한 필터가 추가로 필요!!!
                .addFilterBefore(new JwtAuthenticationFilter(jwtService, jwtUtil, userRepository), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new ExceptionHandlerFilter(objectMapper), JwtAuthenticationFilter.class)
                .cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfigurationSource()))  // CORS 설정 추가
                .csrf(AbstractHttpConfigurer::disable)  // csrf 보호 비활성화
                .httpBasic(HttpBasicConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((requests) -> requests
                        // 스웨거는 권한 없이 접근 가능하도록 설정
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html", "/swagger-resources/**").permitAll()
                        // 특정 권한이 있어야만 특정 API에 접근할 수 있도록 설정
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        // 특정 API들은 별도의 인증/인가 과정 없이도 접근이 가능하도록 설정
                        .requestMatchers(PERMITTED_URI).permitAll()

                        .anyRequest().permitAll())

                // JWT 사용으로 인한 세션 미사용
                .sessionManagement(configure -> configure
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .oauth2Login(customConfigure -> customConfigure
                        .successHandler(oAuth2SuccessHandler)
                        .failureHandler(oAuth2FailureHandler)
                        .userInfoEndpoint(endpointConfig -> endpointConfig.userService(customOAuth2UserService))
                )

                .logout(logout -> logout
                        .logoutUrl("/spring-security-logout")       // 시큐리티 기본 로그아웃 url 변경
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID", "Authorization")
                );

        return httpSecurity.build();
    }
}

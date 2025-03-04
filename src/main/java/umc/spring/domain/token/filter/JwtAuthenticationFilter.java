package umc.spring.domain.token.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import umc.spring.apiPayload.code.status.ErrorStatus;
import umc.spring.apiPayload.exception.handler.ErrorHandler;
import umc.spring.domain.token.JwtUtil;
import umc.spring.domain.token.data.enums.JwtRule;
import umc.spring.domain.user.data.User;
import umc.spring.domain.token.service.JwtService;
import umc.spring.domain.user.repository.UserRepository;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

import static umc.spring.config.security.SecurityConfig.PERMITTED_URI;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

//    @Value("${spring.security.permitted-uris}")
//    private String[] PERMITTED_URI;

    private final JwtService jwtService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 인증이 필요 없는 uri 처리
        if (isPermittedURI(request.getRequestURI())){
            System.out.println(request.getRequestURI() + " : 허가 필요 없음");
            SecurityContextHolder.getContext().setAuthentication(null);
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = jwtService.resolveTokenFromCookie(request, JwtRule.ACCESS_PREFIX);
        String refreshToken = jwtService.resolveTokenFromCookie(request, JwtRule.REFRESH_PREFIX);

        if (Objects.equals(accessToken, "") && Objects.equals(refreshToken, "")) {
            System.out.println("null");
            throw new ErrorHandler(ErrorStatus.JWT_TOKEN_NOT_FOUND);
        }

        if (jwtService.validateAccessToken(accessToken)){
            System.out.println(request.getRequestURI() + " : 유저 정보 확인");
            setAuthenticationToContext(accessToken);
            filterChain.doFilter(request, response);
            return;
        }

        // 리프레시 없을 때 에러 처리 필요
        
        User user = findUserByRefreshToken(refreshToken);

        if (user != null && jwtService.validateRefreshToken(refreshToken, user.getId())) {
            String reissuedAccessToken = jwtService.generateAccessToken(response, user);
            jwtService.generateRefreshToken(response, user);

            setAuthenticationToContext(reissuedAccessToken);
            filterChain.doFilter(request, response);
        }
    }

    private boolean isPermittedURI(String requestURI){
        System.out.println(Arrays.toString(PERMITTED_URI));
        return Arrays.stream(PERMITTED_URI)
                .anyMatch(permitted -> {
                    String replace = permitted.replace("*", "");
                    return requestURI.contains(replace) || replace.contains(requestURI);
                });
    }

    private User findUserByRefreshToken(String refreshToken) {
        String identifier = jwtService.getUserEmail(refreshToken, jwtUtil.getSigningKey(JwtUtil.tokenType.REFRESH));
        return userRepository.findByEmail(identifier).orElseThrow(() -> new ErrorHandler(ErrorStatus.JWT_TOKEN_NOT_FOUND));
    }

    private void setAuthenticationToContext(String accessToken) {
        Authentication authentication = jwtService.getAuthentication(accessToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}

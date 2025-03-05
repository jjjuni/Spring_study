package umc.spring.config.security.OAuth2.filter;

import io.jsonwebtoken.ExpiredJwtException;
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
import umc.spring.apiPayload.exception.ErrorException;
import umc.spring.apiPayload.exception.GeneralException;
import umc.spring.config.security.PermittedUriService;
import umc.spring.domain.token.JwtUtil;
import umc.spring.domain.token.data.enums.JwtRule;
import umc.spring.domain.user.data.User;
import umc.spring.domain.token.service.JwtService;
import umc.spring.domain.user.repository.UserRepository;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final PermittedUriService permittedUriService;
    private final JwtService jwtService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 인증이 필요 없는 uri 처리
        if (permittedUriService.isPermittedURI(request.getRequestURI())){
            System.out.println(request.getRequestURI() + " : 허가 필요 없음");
            SecurityContextHolder.getContext().setAuthentication(null);
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = jwtService.resolveTokenFromCookie(request, JwtRule.ACCESS_PREFIX);
        String refreshToken = jwtService.resolveTokenFromCookie(request, JwtRule.REFRESH_PREFIX);

        try{
            if (jwtService.validateAccessToken(accessToken)){
                System.out.println(request.getRequestURI() + " : 유저 정보 확인");
                setAuthenticationToContext(accessToken);
                filterChain.doFilter(request, response);
                return;
            }
        } catch (ExpiredJwtException e) {
            // 엑세스 토큰이 만료된 경우 재발급 진행

            User user = findUserByRefreshToken(refreshToken);

            if (user != null && jwtService.validateRefreshToken(refreshToken, user.getId())) {
                String reissuedAccessToken = jwtService.generateAccessToken(response, user);
                jwtService.generateRefreshToken(response, user);

                setAuthenticationToContext(reissuedAccessToken);
                filterChain.doFilter(request, response);
            }

        } catch (GeneralException e) {
            System.out.println("필터에서 에러남");
            System.out.println("e.getCode() = " + e.getCode());
            System.out.println("e.getMessage() = " + e.getMessage());
            throw new ErrorException(ErrorStatus.JWT_TOKEN_NOT_FOUND);
        }
    }

    private User findUserByRefreshToken(String refreshToken) {
        String identifier = jwtService.getUserEmail(refreshToken, jwtUtil.getSigningKey(JwtUtil.tokenType.REFRESH));
        return userRepository.findByEmail(identifier).orElseThrow(() -> new ErrorException(ErrorStatus.JWT_TOKEN_NOT_FOUND));
    }

    private void setAuthenticationToContext(String accessToken) {
        Authentication authentication = jwtService.getAuthentication(accessToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}

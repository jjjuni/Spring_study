package umc.spring.domain.token.service;

import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.spring.apiPayload.code.status.ErrorStatus;
import umc.spring.apiPayload.exception.handler.ErrorHandler;
import umc.spring.config.security.CustomUserDetailsService;
import umc.spring.domain.user.data.User;
import umc.spring.domain.user.data.enums.Role;
import umc.spring.domain.token.JwtGenerator;
import umc.spring.domain.token.JwtUtil;
import umc.spring.domain.token.data.RefreshToken;
import umc.spring.domain.token.data.enums.JwtRule;
import umc.spring.domain.token.data.enums.TokenStatus;
import umc.spring.domain.token.repository.RefreshTokenRepository;

import java.security.Key;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtService {
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtGenerator jwtGenerator;
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${spring.jwt.access-secret}")
    private String ACCESS_SECRET;

    @Value("${spring.jwt.refresh-secret}")
    private String REFRESH_SECRET;

    @Value("${spring.jwt.access-expiration}")
    private long ACCESS_EXPIRATION;

    @Value("${spring.jwt.refresh-expiration}")
    private long REFRESH_EXPIRATION;

    private Key accessSecret;
    private Key refreshSecret;

    @PostConstruct
    public void init() {
        this.accessSecret = jwtUtil.getSigningKey(ACCESS_SECRET);
        this.refreshSecret = jwtUtil.getSigningKey(REFRESH_SECRET);
    }

    public void validateUser(User request) {
        if(request.getRole() == Role.NOT_REGISTERED) {
            throw new ErrorHandler(ErrorStatus.NOT_AUTHENTICATED);
        }
    }

    public String generateAccessToken(HttpServletResponse response, User request){
        String accessToken = jwtGenerator.generateAccessToken(accessSecret, ACCESS_EXPIRATION, request);
        ResponseCookie cookie = setTokenToCookie(JwtRule.ACCESS_PREFIX.getValue(), accessToken, ACCESS_EXPIRATION / 1000);
        response.addHeader(JwtRule.JWT_ISSUE_HEADER.getValue(), cookie.toString());

        return accessToken;
    }

    @Transactional
    public void generateRefreshToken(HttpServletResponse response, User request){
        String refreshToken = jwtGenerator.generateRefreshToken(refreshSecret, REFRESH_EXPIRATION, request);
        ResponseCookie cookie = setTokenToCookie(JwtRule.REFRESH_PREFIX.getValue(), refreshToken, REFRESH_EXPIRATION / 1000);
        response.addHeader(JwtRule.JWT_ISSUE_HEADER.getValue(), cookie.toString());

        refreshTokenRepository.findByUserId(request.getId())
                .ifPresent(refreshTokenRepository::delete);

        RefreshToken newRefreshToken = RefreshToken.builder()
                .token(refreshToken)
                .userId(request.getId())
                .build();

        refreshTokenRepository.save(newRefreshToken);
    }

    public ResponseCookie setTokenToCookie(String tokenPrefix, String token , long maxAgeSeconds){
        return ResponseCookie.from(tokenPrefix, token)
                .path("/")
                .maxAge(maxAgeSeconds)
                .httpOnly(true)
                .sameSite("None")
                .secure(true)
                .build();
    }

    public boolean validateAccessToken(String token){
        return jwtUtil.getTokenStatus(token, accessSecret) == TokenStatus.AUTHENTICATED;
    }

    public boolean validateRefreshToken(String token, Long userId){
        boolean isRefreshValid = jwtUtil.getTokenStatus(token, refreshSecret) == TokenStatus.AUTHENTICATED;

        RefreshToken storedToken = refreshTokenRepository.findByUserId(userId)
                .orElseThrow(() -> new ErrorHandler(ErrorStatus.INVALID_TOKEN));

        boolean isTokenMatched = Objects.equals(storedToken.getToken(), token);

        return isRefreshValid && isTokenMatched;
    }

    public String resolveTokenFromCookie(HttpServletRequest request, JwtRule tokenPrefix){
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        return jwtUtil.resolveTokenFromCookie(cookies, tokenPrefix);
    }

    public Authentication getAuthentication(String token){
        UserDetails principal = customUserDetailsService.loadUserByUsername(getUserEmail(token, accessSecret));
        return new UsernamePasswordAuthenticationToken(principal, "", principal.getAuthorities());
    }

    // 토큰의 subject(유저 email)을 반환
    public String getUserEmail(String token, Key serectKey){
        return Jwts.parserBuilder()
                .setSigningKey(serectKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // refreshToken을 identifier로 하여 유저의 email 반환
    public String getIdentifierFromRefresh(String refreshToken) {
        try{
            return Jwts.parserBuilder()
                    .setSigningKey(refreshSecret)
                    .build()
                    .parseClaimsJws(refreshToken)
                    .getBody()
                    .getSubject();
        } catch (Exception e){
            return null;
        }
    }

    public void logout(User request, HttpServletResponse response) {

        refreshTokenRepository.deleteByUserId(request.getId());

        Cookie accessCookie = jwtUtil.resetToken(JwtRule.ACCESS_PREFIX);
        Cookie refreshCookie = jwtUtil.resetToken(JwtRule.REFRESH_PREFIX);

        response.addCookie(accessCookie);
        response.addCookie(refreshCookie);
    }
}

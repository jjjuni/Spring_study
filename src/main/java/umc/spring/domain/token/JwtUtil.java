package umc.spring.domain.token;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.spring.domain.token.data.enums.JwtRule;
import umc.spring.domain.token.data.enums.TokenStatus;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Base64;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JwtUtil {

    public enum tokenType {
        ACCESS, REFRESH;
    }

    @Value("${spring.jwt.access-secret}")
    private String ACCESS_SECRET;

    @Value("${spring.jwt.refresh-secret}")
    private String REFRESH_SECRET;

    public TokenStatus getTokenStatus(String token, Key secretKey) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return TokenStatus.AUTHENTICATED;
        } catch (ExpiredJwtException e) {
            log.error("토큰이 만료되었습니다: {}", e.getMessage());
            return TokenStatus.EXPIRED;
        } catch (MalformedJwtException e) {
            log.error("잘못된 토큰 형식입니다: {}", e.getMessage());
            return TokenStatus.INVALID;
        } catch (UnsupportedJwtException e) {
            log.error("지원되지 않는 토큰입니다: {}", e.getMessage());
            return TokenStatus.INVALID;
        } catch (IllegalArgumentException | JwtException e) {
            log.error("토큰 형식이 잘못되었습니다: {}", e.getMessage());
            return TokenStatus.INVALID;
        }
    }

    public String resolveTokenFromCookie(Cookie[] cookies, JwtRule tokenPrefix) {
        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(tokenPrefix.getValue()))
                .findFirst()
                .map(Cookie::getValue)
                .orElse("");
    }

    public Key getSigningKey(tokenType token){
        return switch (token) {
            case ACCESS -> Keys.hmacShaKeyFor(encodeToBase64(ACCESS_SECRET).getBytes(StandardCharsets.UTF_8));
            case REFRESH -> Keys.hmacShaKeyFor(encodeToBase64(REFRESH_SECRET).getBytes(StandardCharsets.UTF_8));
            default -> null ;
        };
    }

    public String encodeToBase64(String secretKey){
        return Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public Cookie resetToken(JwtRule tokenPrefix){
        Cookie cookie = new Cookie(tokenPrefix.getValue(), null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        return cookie;
    }
}

package umc.spring.config.security.OAuth2.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import umc.spring.apiPayload.code.status.ErrorStatus;
import umc.spring.apiPayload.exception.ErrorException;
import umc.spring.domain.token.service.JwtService;
import umc.spring.domain.user.data.User;
import umc.spring.domain.user.data.enums.Role;
import umc.spring.domain.user.repository.UserRepository;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        final String provider = token.getAuthorizedClientRegistrationId();

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = oAuth2User.getAttributes();

        String email = "";

        switch (provider) {
            case "kakao" -> {
                Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
                email = (String) kakaoAccount.get("email");
            }
            case "naver" -> {
                Map<String, Object> naverAccount = (Map<String, Object>) attributes.get("response");
                email = (String) naverAccount.get("email");
            }
            case "google" -> {
                email = (String) attributes.get("email");
            }
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ErrorException(ErrorStatus.USER_NOT_FOUND));

        jwtService.generateRefreshToken(response, user);
        jwtService.generateAccessToken(response, user);

        String redirectUrl = "http://localhost:3000";

        if (user.getRole() == Role.UNKNOWN){
            redirectUrl = "http://localhost:3000/signup";
        }

        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
}

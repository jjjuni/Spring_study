package umc.spring.domain.auth.web.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.web.bind.annotation.*;
import umc.spring.apiPayload.ApiResponse;
import umc.spring.apiPayload.code.status.ErrorStatus;
import umc.spring.apiPayload.exception.ErrorException;
import umc.spring.config.security.OAuth2.CustomOAuth2UserService;
import umc.spring.config.security.OAuth2.UserPrincipal;
import umc.spring.domain.auth.converter.AuthConverter;
import umc.spring.domain.auth.web.dto.AuthRequestDTO;
import umc.spring.domain.auth.web.dto.AuthResponseDTO;
import umc.spring.domain.token.JwtUtil;
import umc.spring.domain.token.data.enums.JwtRule;
import umc.spring.domain.token.service.JwtService;
import umc.spring.domain.user.data.User;
import umc.spring.domain.user.repository.UserRepository;
import umc.spring.domain.user.service.UserCommandService;
import umc.spring.domain.user.service.UserCommandServiceImpl;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final UserCommandService userCommandService;
    private final JwtService jwtService;
    private final JwtUtil jwtUtil;
    private final CustomOAuth2UserService oAuth2UserService;

    @Value("${spring.jwt.refresh-secret}")
    private String REFRESH_SECRET;

    @PostMapping("/register")
    public ApiResponse<AuthResponseDTO.AuthResultDTO> generateToken(HttpServletResponse response, @RequestBody AuthRequestDTO.AuthDTO request){
        User requestUser = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ErrorException(ErrorStatus.USER_NOT_FOUND));

        jwtService.generateAccessToken(response, requestUser);
        jwtService.generateRefreshToken(response, requestUser);

        AuthResponseDTO.AuthResultDTO authResultDTO = AuthResponseDTO.AuthResultDTO.builder()
                .now(LocalDateTime.now())
                .build();

        return ApiResponse.onSuccess(authResultDTO);
    }

    @PostMapping("/logout")
    public ApiResponse<AuthResponseDTO.LogoutResultDTO> logout(HttpServletRequest request, HttpServletResponse response) {

        String accessToken = jwtService.resolveTokenFromCookie(request, JwtRule.ACCESS_PREFIX);

        User user = userRepository.findByEmail(jwtService.getUserSubject(accessToken, jwtUtil.getSigningKey(JwtUtil.tokenType.ACCESS))).orElse(null);

        try {
            jwtService.logout(user, response);
            System.out.println("complete");
        } catch (Exception e){
            // 예상하지 못한 타입일 경우 처리
            System.out.println(e);
            System.out.println("logout Post 이상");
        }

        return ApiResponse.onSuccess(AuthResponseDTO.LogoutResultDTO.builder().createdAt(LocalDateTime.now()).build());
    }

    @GetMapping("/me")
    public ApiResponse<AuthResponseDTO.UserInfoDTO> getUserInfo(HttpServletRequest request, HttpServletResponse response, @AuthenticationPrincipal UserPrincipal userDetails) {
        String email = userDetails.getUsername();

        User user = userCommandService.userInfo(email);

        return ApiResponse.onSuccess(AuthConverter.toUserInfoDTO(user));
    }
}

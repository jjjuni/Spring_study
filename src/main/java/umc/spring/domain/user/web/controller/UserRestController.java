package umc.spring.domain.user.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import umc.spring.apiPayload.ApiResponse;
import umc.spring.domain.token.data.enums.JwtRule;
import umc.spring.domain.token.service.JwtService;
import umc.spring.domain.user.converter.UserConverter;
import umc.spring.domain.user.data.User;
import umc.spring.domain.user.repository.UserRepository;
import umc.spring.domain.user.service.UserCommandService;
import umc.spring.domain.user.web.dto.UserRequestDTO;
import umc.spring.domain.user.web.dto.UserResponseDTO;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@Validated
public class UserRestController {

    private final UserCommandService userCommandService;

    private final UserRepository userRepository;

    private final JwtService jwtService;

    @PostMapping("/user")
    public ApiResponse<UserResponseDTO.JoinResultDTO> join(@RequestBody @Valid UserRequestDTO.JoinDTO request){
        User user = userCommandService.joinUser(request);
        return ApiResponse.onSuccess(UserConverter.toJoinResultDTO(user));
    }

    @PostMapping("/logout")
    public ApiResponse<UserResponseDTO.LogoutResultDTO> logout(HttpServletRequest request, HttpServletResponse response) {

        String refreshToken = jwtService.resolveTokenFromCookie(request, JwtRule.REFRESH_PREFIX);

        User user = userRepository.findByEmail(jwtService.getIdentifierFromRefresh(refreshToken)).orElse(null);

        try {
            jwtService.logout(user, response);
            System.out.println("complete");
        } catch (Exception e){
            // 예상하지 못한 타입일 경우 처리
            System.out.println(e);
            System.out.println("logout Post 이상");
        }

        return ApiResponse.onSuccess(UserResponseDTO.LogoutResultDTO.builder().createdAt(LocalDateTime.now()).build());
    }
}

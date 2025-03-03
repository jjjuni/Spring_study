package umc.spring.domain.auth.web.controller;


import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import umc.spring.apiPayload.ApiResponse;
import umc.spring.apiPayload.code.status.ErrorStatus;
import umc.spring.apiPayload.exception.handler.ErrorHandler;
import umc.spring.domain.auth.web.dto.AuthRequestDTO;
import umc.spring.domain.auth.web.dto.AuthResponseDTO;
import umc.spring.domain.token.service.JwtService;
import umc.spring.domain.user.data.User;
import umc.spring.domain.user.repository.UserRepository;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class AuthController {
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @PostMapping("/auth")
    public ApiResponse<AuthResponseDTO.AuthResultDTO> generateToken(HttpServletResponse response, @RequestBody AuthRequestDTO.AuthDTO request){
        User requestUser = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ErrorHandler(ErrorStatus.USER_NOT_FOUND));
        jwtService.validateUser(requestUser);

        jwtService.generateAccessToken(response, requestUser);
        jwtService.generateRefreshToken(response, requestUser);

        AuthResponseDTO.AuthResultDTO authResultDTO = AuthResponseDTO.AuthResultDTO.builder()
                .now(LocalDateTime.now())
                .build();

        return ApiResponse.onSuccess(authResultDTO);
    }
}

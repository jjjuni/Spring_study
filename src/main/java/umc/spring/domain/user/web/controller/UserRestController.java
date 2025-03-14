package umc.spring.domain.user.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import umc.spring.apiPayload.ApiResponse;
import umc.spring.domain.token.data.enums.JwtRule;
import umc.spring.domain.token.service.JwtService;
import umc.spring.domain.user.converter.UserConverter;
import umc.spring.domain.user.data.User;
import umc.spring.domain.user.data.enums.Role;
import umc.spring.domain.user.repository.UserRepository;
import umc.spring.domain.user.service.UserCommandService;
import umc.spring.domain.user.service.UserCommandServiceImpl;
import umc.spring.domain.user.web.dto.UserRequestDTO;
import umc.spring.domain.user.web.dto.UserResponseDTO;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/user")
public class UserRestController {

    private final UserCommandService userCommandService;

    private final UserRepository userRepository;

    private final JwtService jwtService;

    @PostMapping("")
    public ApiResponse<UserResponseDTO.JoinResultDTO> join(@RequestBody @Valid UserRequestDTO.JoinDTO request){
        User user = userCommandService.joinUser(request);
        return ApiResponse.onSuccess(UserConverter.toJoinResultDTO(user));
    }

    @PostMapping("/role")
    public ApiResponse<UserResponseDTO.ChangeRoleResultDTO> changeRole(@RequestBody UserRequestDTO.changeRoleDTO request, HttpServletRequest httpServletRequest) {

        String accessToken = jwtService.resolveTokenFromCookie(httpServletRequest, JwtRule.ACCESS_PREFIX);

        User user = jwtService.getUserFromAccess(accessToken);

        User updateUser = userCommandService.changeRole(user, request.getRole());

        return ApiResponse.onSuccess(UserConverter.toChangeRoleResultDTO(updateUser));
    }
}

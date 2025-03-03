package umc.spring.domain.user.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import umc.spring.apiPayload.ApiResponse;
import umc.spring.domain.user.converter.UserConverter;
import umc.spring.domain.user.data.User;
import umc.spring.domain.user.service.UserCommandService;
import umc.spring.domain.user.web.dto.UserRequestDTO;
import umc.spring.domain.user.web.dto.UserResponseDTO;

@RestController
@RequiredArgsConstructor
@Validated
public class UserRestController {

    private final UserCommandService userCommandService;

    @PostMapping("/user")
    public ApiResponse<UserResponseDTO.JoinResultDTO> join(@RequestBody @Valid UserRequestDTO.JoinDTO request){
        User user = userCommandService.joinUser(request);
        return ApiResponse.onSuccess(UserConverter.toJoinResultDTO(user));
    }
}

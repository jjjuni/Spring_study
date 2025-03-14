package umc.spring.domain.auth.converter;

import umc.spring.domain.auth.web.dto.AuthResponseDTO;
import umc.spring.domain.user.data.User;

public class AuthConverter {

    public static AuthResponseDTO.UserInfoDTO toUserInfoDTO(User user) {
        return AuthResponseDTO.UserInfoDTO.builder()
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}

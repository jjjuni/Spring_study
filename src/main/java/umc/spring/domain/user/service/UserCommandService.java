package umc.spring.domain.user.service;

import umc.spring.domain.user.data.User;
import umc.spring.domain.user.web.dto.UserRequestDTO;

public interface UserCommandService {
    public User joinUser(UserRequestDTO.JoinDTO request);
}

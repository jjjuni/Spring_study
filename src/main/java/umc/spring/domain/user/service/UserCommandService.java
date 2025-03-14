package umc.spring.domain.user.service;

import umc.spring.domain.user.data.User;
import umc.spring.domain.user.data.enums.Role;
import umc.spring.domain.user.web.dto.UserRequestDTO;

public interface UserCommandService {
    public User joinUser(UserRequestDTO.JoinDTO request);
    public User userInfo(String email);
    public User changeRole(User user, Role role);
}

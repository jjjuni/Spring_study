package umc.spring.domain.user.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import umc.spring.apiPayload.code.status.ErrorStatus;
import umc.spring.apiPayload.exception.ErrorException;
import umc.spring.domain.user.converter.UserConverter;
import umc.spring.domain.user.converter.UserPreferConverter;
import umc.spring.domain.foodcategory.data.FoodCategory;
import umc.spring.domain.user.data.User;
import umc.spring.domain.user.data.mapping.UserPrefer;
import umc.spring.domain.foodcategory.repository.FoodCategoryRepository;
import umc.spring.domain.user.repository.UserRepository;
import umc.spring.domain.user.web.dto.UserRequestDTO;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;

    private final FoodCategoryRepository foodCategoryRepository;

    @Lazy
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User joinUser(UserRequestDTO.JoinDTO request) {

        User newUser = UserConverter.toUser(request);

        newUser.encodePassword(passwordEncoder.encode(request.getPassword()));

        List<FoodCategory> foodCategoryList = request.getPreferCategory().stream()
                .map(category -> foodCategoryRepository.findById(category).orElseThrow(() -> new ErrorException(ErrorStatus.FOOD_CATEGORY_NOT_FOUND)))
                .collect(Collectors.toList());

        List<UserPrefer> userPreferList = UserPreferConverter.toUserPreferList(foodCategoryList);

        userPreferList.forEach(userPrefer -> {userPrefer.setUser(newUser);});

        return userRepository.save(newUser);
    }
}

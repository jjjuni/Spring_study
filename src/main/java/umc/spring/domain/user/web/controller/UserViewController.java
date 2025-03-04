package umc.spring.domain.user.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import umc.spring.domain.token.service.JwtService;
import umc.spring.domain.user.repository.UserRepository;
import umc.spring.domain.user.service.UserCommandService;
import umc.spring.domain.user.web.dto.UserRequestDTO;

@Controller
@RequiredArgsConstructor
public class UserViewController {

    private final UserCommandService userCommandService;

    private final UserRepository userRepository;

    private final JwtService jwtService;

    @PostMapping("/users/signup")
    public String joinUser(@ModelAttribute("userJoinDTO") UserRequestDTO.JoinDTO request,
                             BindingResult bindingResult,
                             Model model) {

        if (bindingResult.hasErrors()) {
            return "signup";
        }

        try {
            userCommandService.joinUser(request);
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "signup";
        }
    }

//    @PostMapping("/logout")
//    public String logout(HttpServletRequest request, HttpServletResponse response) {
//
//        String refreshToken = jwtService.resolveTokenFromCookie(request, JwtRule.REFRESH_PREFIX);
//
//        User user = userRepository.findByEmail(jwtService.getIdentifierFromRefresh(refreshToken)).orElse(null);
//
//        try {
//            jwtService.logout(user, response);
//            System.out.println("complete");
//        } catch (Exception e){
//            // 예상하지 못한 타입일 경우 처리
//            System.out.println(e);
//            System.out.println("logout Post 이상");
//            return "redirect:/login";
//        }
//
//        return "redirect:/login";
//    }

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    @GetMapping("/signup")
    public String signupPage(Model model, UserRequestDTO.JoinDTO JoinDTO){
        model.addAttribute("userJoinDTO", JoinDTO);
        return "signup";
    }

    @GetMapping("/home")
    public String home(){
        return "home";
    }

    @GetMapping("/admin")
    public String admin(){
        return "admin";
    }
}

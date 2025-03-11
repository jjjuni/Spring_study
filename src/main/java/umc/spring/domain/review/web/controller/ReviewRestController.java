package umc.spring.domain.review.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.server.Cookie;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import umc.spring.apiPayload.ApiResponse;
import umc.spring.domain.review.converter.ReviewConverter;
import umc.spring.domain.review.data.Review;
import umc.spring.domain.review.service.ReviewCommandService;
import umc.spring.domain.review.web.dto.ReviewRequestDTO;
import umc.spring.domain.review.web.dto.ReviewResponseDTO;
import umc.spring.domain.token.data.enums.JwtRule;
import umc.spring.domain.token.service.JwtService;
import umc.spring.domain.user.data.User;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewRestController {

    private final ReviewCommandService reviewCommandService;

    private final JwtService jwtService;

    @PostMapping("")
    public ApiResponse<ReviewResponseDTO.AddReviewResultDTO> join(@RequestBody @Valid ReviewRequestDTO.AddReviewDTO reviewRequest, HttpServletRequest request){

        String accessToken = jwtService.resolveTokenFromCookie(request, JwtRule.ACCESS_PREFIX);

        User user = jwtService.getUserFromAccess(accessToken);
        Review review = reviewCommandService.addReview(reviewRequest, user);
        return ApiResponse.onSuccess(ReviewConverter.toAddReviewResultDTO(review));
    }
}

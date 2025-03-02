package umc.spring.domain.Review.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import umc.spring.apiPayload.ApiResponse;
import umc.spring.domain.Review.converter.ReviewConverter;
import umc.spring.domain.Review.data.Review;
import umc.spring.domain.Review.service.ReviewCommandService;
import umc.spring.domain.Review.web.dto.ReviewRequestDTO;
import umc.spring.domain.Review.web.dto.ReviewResponseDTO;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewRestController {

    private final ReviewCommandService reviewCommandService;

    @PostMapping("")
    public ApiResponse<ReviewResponseDTO.AddReviewResultDTO> join(@RequestBody @Valid ReviewRequestDTO.AddReviewDTO request){
        Review review = reviewCommandService.addReview(request);
        return ApiResponse.onSuccess(ReviewConverter.toAddReviewResultDTO(review));
    }
}

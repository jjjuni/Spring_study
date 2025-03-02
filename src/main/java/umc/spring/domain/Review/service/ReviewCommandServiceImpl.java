package umc.spring.domain.Review.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.spring.apiPayload.code.status.ErrorStatus;
import umc.spring.apiPayload.exception.handler.ErrorHandler;
import umc.spring.domain.Review.converter.ReviewConverter;
import umc.spring.domain.Member.data.Member;
import umc.spring.domain.Review.data.Review;
import umc.spring.domain.Store.data.Store;
import umc.spring.domain.Member.repository.MemberRepository;
import umc.spring.domain.Review.repository.ReviewRepository;
import umc.spring.domain.Store.repository.StoreRepository;
import umc.spring.domain.Review.web.dto.ReviewRequestDTO;

@Service
@RequiredArgsConstructor
public class ReviewCommandServiceImpl implements ReviewCommandService{

    private final ReviewRepository reviewRepository;

    private final MemberRepository memberRepository;

    private final StoreRepository storeRepository;

    @Override
    @Transactional
    public Review addReview(ReviewRequestDTO.AddReviewDTO request) {

        Review newReview = ReviewConverter.toReview(request);
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new ErrorHandler(ErrorStatus.MEMBER_NOT_FOUND));
        Store store = storeRepository.findById(request.getStoreId())
                .orElseThrow(() -> new ErrorHandler(ErrorStatus.STORE_NOT_FOUND));

        newReview.setMember(member);
        newReview.setStore(store);

        return reviewRepository.save(newReview);
    }
}

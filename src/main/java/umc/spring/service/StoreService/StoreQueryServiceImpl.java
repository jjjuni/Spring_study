package umc.spring.service.StoreService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import umc.spring.domain.Review;
import umc.spring.domain.Store;
import umc.spring.repository.ReviewRepository.ReviewRepository;
import umc.spring.repository.StoreRepository.StoreRepository;

@Service
@RequiredArgsConstructor
public class StoreQueryServiceImpl implements StoreQueryService{

    private final StoreRepository storeRepository;

    private final ReviewRepository reviewRepository;

    @Override
    public Page<Review> getReviewList(Long storeId, Integer page) {

        Store store = storeRepository.findById(storeId).get();

        return reviewRepository.findAllByStore(store, PageRequest.of(page, 10));
    }
}

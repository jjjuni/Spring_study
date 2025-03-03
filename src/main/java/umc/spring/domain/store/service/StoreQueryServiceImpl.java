package umc.spring.domain.store.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import umc.spring.domain.mission.data.Mission;
import umc.spring.domain.review.data.Review;
import umc.spring.domain.store.data.Store;
import umc.spring.domain.mission.repository.MissionRepository;
import umc.spring.domain.review.repository.ReviewRepository;
import umc.spring.domain.store.repository.StoreRepository;

@Service
@RequiredArgsConstructor
public class StoreQueryServiceImpl implements StoreQueryService{

    private final StoreRepository storeRepository;

    private final ReviewRepository reviewRepository;

    private final MissionRepository missionRepository;

    @Override
    public Page<Review> getReviewList(Long storeId, Integer page) {

        Store store = storeRepository.findById(storeId).get();

        return reviewRepository.findAllByStore(store, PageRequest.of(page, 10));
    }

    @Override
    public Page<Mission> getMissionList(Long storeId, Integer page) {

        Store store = storeRepository.findById(storeId).get();

        return missionRepository.findAllByStore(store, PageRequest.of(page, 10));
    }
}

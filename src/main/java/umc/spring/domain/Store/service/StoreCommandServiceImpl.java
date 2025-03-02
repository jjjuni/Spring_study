package umc.spring.domain.Store.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.spring.apiPayload.code.status.ErrorStatus;
import umc.spring.apiPayload.exception.handler.ErrorHandler;
import umc.spring.domain.Store.converter.StoreConverter;
import umc.spring.domain.Region.data.Region;
import umc.spring.domain.Store.data.Store;
import umc.spring.domain.Region.repository.RegionRepository;
import umc.spring.domain.Store.repository.StoreRepository;
import umc.spring.domain.Store.web.dto.StoreRequestDTO;

@Service
@RequiredArgsConstructor
public class StoreCommandServiceImpl implements StoreCommandService{

    private final StoreRepository storeRepository;

    private final RegionRepository regionRepository;

    @Override
    @Transactional
    public Store addStore(StoreRequestDTO.AddStoreDTO request) {

        Store newStore = StoreConverter.toStore(request);
        Region region = regionRepository.findById(request.getRegionId())
                .orElseThrow(() -> new ErrorHandler(ErrorStatus.REGION_NOT_FOUND));

        newStore.setRegion(region);

        return storeRepository.save(newStore);
    }
}

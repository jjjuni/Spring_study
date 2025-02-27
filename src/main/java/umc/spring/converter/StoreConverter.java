package umc.spring.converter;

import umc.spring.domain.Store;
import umc.spring.web.dto.StoreDTO.StoreRequestDTO;
import umc.spring.web.dto.StoreDTO.StoreResponseDTO;

import java.time.LocalDateTime;

public class StoreConverter {

    public static StoreResponseDTO.addStoreDTO toAddStoreResultDTO(Store store) {
        return StoreResponseDTO.addStoreDTO.builder()
                .storeId((store.getId()))
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static Store toStore(StoreRequestDTO.AddStoreDTO request){

        return Store.builder()
                .name(request.getName())
                .build();
    }
}

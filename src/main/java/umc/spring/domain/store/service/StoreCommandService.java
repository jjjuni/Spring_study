package umc.spring.domain.store.service;

import umc.spring.domain.store.data.Store;
import umc.spring.domain.store.web.dto.StoreRequestDTO;

public interface StoreCommandService {
    public Store addStore(StoreRequestDTO.AddStoreDTO request);
}

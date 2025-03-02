package umc.spring.domain.Store.service;

import umc.spring.domain.Store.data.Store;
import umc.spring.domain.Store.web.dto.StoreRequestDTO;

public interface StoreCommandService {
    public Store addStore(StoreRequestDTO.AddStoreDTO request);
}

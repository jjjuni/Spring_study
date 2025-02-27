package umc.spring.web.dto.StoreDTO;

import lombok.Getter;

public class StoreRequestDTO {

    @Getter
    public static class AddStoreDTO {
        String name;
        String address;
        Integer regionId;
    }
}

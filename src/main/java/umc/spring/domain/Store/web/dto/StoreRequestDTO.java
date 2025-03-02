package umc.spring.domain.Store.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public class StoreRequestDTO {

    @Getter
    public static class AddStoreDTO {
        @NotNull(message = "name은 필수 항목입니다.")
        String name;
        @NotNull(message = "address는 필수 항목입니다.")
        String address;
        @NotNull(message = "regionId는 필수 항목입니다.")
        Long regionId;
    }
}

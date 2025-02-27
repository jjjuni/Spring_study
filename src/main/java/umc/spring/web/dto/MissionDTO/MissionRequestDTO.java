package umc.spring.web.dto.MissionDTO;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import umc.spring.validation.annotation.ExistCategories;

import java.time.LocalDate;
import java.util.List;

public class MissionRequestDTO {

    @Getter
    public static class AddMissionDTO{

        @NotNull
        Long storeId;
        @NotNull
        Integer reward;
        @NotNull
        String missionSpec;
    }
}

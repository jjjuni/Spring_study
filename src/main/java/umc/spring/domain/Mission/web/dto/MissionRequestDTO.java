package umc.spring.domain.Mission.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

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

    @Getter
    public static class ChallengeMissionDTO{

        @NotNull
        Long memberId;
        @NotNull
        Long missionId;
    }
}

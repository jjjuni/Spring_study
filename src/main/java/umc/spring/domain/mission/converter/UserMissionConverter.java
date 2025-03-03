package umc.spring.domain.mission.converter;

import umc.spring.domain.mission.data.enums.MissionStatus;
import umc.spring.domain.mission.data.mapping.UserMission;
import umc.spring.domain.mission.web.dto.MissionRequestDTO;
import umc.spring.domain.mission.web.dto.MissionResponseDTO;

import java.time.LocalDateTime;

public class UserMissionConverter {

    public static MissionResponseDTO.ChallengeMissionResultDTO toChallengeMissionResultDTO(UserMission userMission){
        return MissionResponseDTO.ChallengeMissionResultDTO.builder()
                .userMissionId(userMission.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static UserMission toUserMission(MissionRequestDTO.ChallengeMissionDTO request){

        MissionStatus missionStatus = MissionStatus.CHALLENGING;

        return UserMission.builder()
                .status(missionStatus)
                .build();
    }
}

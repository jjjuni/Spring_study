package umc.spring.domain.mission.converter;

import umc.spring.domain.mission.data.Mission;
import umc.spring.domain.mission.web.dto.MissionRequestDTO;
import umc.spring.domain.mission.web.dto.MissionResponseDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class MissionConverter {

    public static MissionResponseDTO.AddMissionResultDTO toAddMissionResultDTO(Mission mission){
        return MissionResponseDTO.AddMissionResultDTO.builder()
                .missionId(mission.getId())
                .createdAt(LocalDateTime.now())
                .deadline(mission.getDeadline())
                .build();
    }

    public static Mission toMission(MissionRequestDTO.AddMissionDTO request){
        return Mission.builder()
                .reward(request.getReward())
                .missionSpec(request.getMissionSpec())
                .deadline(LocalDate.now().plusDays(7))
                .build();
    }
}

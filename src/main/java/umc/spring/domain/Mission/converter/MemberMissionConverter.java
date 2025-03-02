package umc.spring.domain.Mission.converter;

import umc.spring.domain.Mission.data.enums.MissionStatus;
import umc.spring.domain.Mission.data.mapping.MemberMission;
import umc.spring.domain.Mission.web.dto.MissionRequestDTO;
import umc.spring.domain.Mission.web.dto.MissionResponseDTO;

import java.time.LocalDateTime;

public class MemberMissionConverter {

    public static MissionResponseDTO.ChallengeMissionResultDTO toChallengeMissionResultDTO(MemberMission memberMission){
        return MissionResponseDTO.ChallengeMissionResultDTO.builder()
                .memberMissionId(memberMission.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static MemberMission toMemberMission(MissionRequestDTO.ChallengeMissionDTO request){

        MissionStatus missionStatus = MissionStatus.CHALLENGING;

        return MemberMission.builder()
                .status(missionStatus)
                .build();
    }
}

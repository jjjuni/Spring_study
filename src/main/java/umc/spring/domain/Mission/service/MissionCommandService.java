package umc.spring.domain.Mission.service;

import umc.spring.domain.Mission.data.Mission;
import umc.spring.domain.Mission.data.mapping.MemberMission;
import umc.spring.domain.Mission.web.dto.MissionRequestDTO;

public interface MissionCommandService {
    public Mission addMission(MissionRequestDTO.AddMissionDTO request);

    public MemberMission challengeMission(MissionRequestDTO.ChallengeMissionDTO request);
}

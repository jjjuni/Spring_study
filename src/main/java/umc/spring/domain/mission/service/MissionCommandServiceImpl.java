package umc.spring.domain.mission.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.spring.apiPayload.code.status.ErrorStatus;
import umc.spring.apiPayload.exception.ErrorException;
import umc.spring.domain.mission.converter.UserMissionConverter;
import umc.spring.domain.mission.converter.MissionConverter;
import umc.spring.domain.user.data.User;
import umc.spring.domain.mission.data.Mission;
import umc.spring.domain.store.data.Store;
import umc.spring.domain.mission.data.mapping.UserMission;
import umc.spring.domain.mission.repository.UserMissionRepository;
import umc.spring.domain.user.repository.UserRepository;
import umc.spring.domain.mission.repository.MissionRepository;
import umc.spring.domain.store.repository.StoreRepository;
import umc.spring.domain.mission.web.dto.MissionRequestDTO;

@Service
@RequiredArgsConstructor
public class MissionCommandServiceImpl implements MissionCommandService{

    private final MissionRepository missionRepository;

    private final StoreRepository storeRepository;

    private final UserMissionRepository userMissionRepository;

    private final UserRepository userRepository;

    @Override
    public Mission addMission(MissionRequestDTO.AddMissionDTO request) {

        Mission newMission = MissionConverter.toMission(request);
        Store store = storeRepository.findById(request.getStoreId()).orElseThrow(() -> new ErrorException(ErrorStatus.STORE_NOT_FOUND));

        newMission.setStore(store);

        return missionRepository.save(newMission);
    }

    @Override
    public UserMission challengeMission(MissionRequestDTO.ChallengeMissionDTO request) {

        UserMission newUserMission = UserMissionConverter.toUserMission(request);
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new ErrorException(ErrorStatus.USER_NOT_FOUND));
        Mission mission = missionRepository.findById(request.getMissionId()).orElseThrow(() -> new ErrorException(ErrorStatus.MISSION_NOT_FOUND));

        newUserMission.setUser(user);
        newUserMission.setMission(mission);

        return userMissionRepository.save(newUserMission);
    }
}

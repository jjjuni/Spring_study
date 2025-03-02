package umc.spring.domain.Mission.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.spring.apiPayload.code.status.ErrorStatus;
import umc.spring.apiPayload.exception.handler.ErrorHandler;
import umc.spring.domain.Mission.converter.MemberMissionConverter;
import umc.spring.domain.Mission.converter.MissionConverter;
import umc.spring.domain.Member.data.Member;
import umc.spring.domain.Mission.data.Mission;
import umc.spring.domain.Store.data.Store;
import umc.spring.domain.Mission.data.mapping.MemberMission;
import umc.spring.domain.Mission.repository.MemberMissionRepository;
import umc.spring.domain.Member.repository.MemberRepository;
import umc.spring.domain.Mission.repository.MissionRepository;
import umc.spring.domain.Store.repository.StoreRepository;
import umc.spring.domain.Mission.web.dto.MissionRequestDTO;

@Service
@RequiredArgsConstructor
public class MissionCommandServiceImpl implements MissionCommandService{

    private final MissionRepository missionRepository;

    private final StoreRepository storeRepository;

    private final MemberMissionRepository memberMissionRepository;

    private final MemberRepository memberRepository;

    @Override
    public Mission addMission(MissionRequestDTO.AddMissionDTO request) {

        Mission newMission = MissionConverter.toMission(request);
        Store store = storeRepository.findById(request.getStoreId()).orElseThrow(() -> new ErrorHandler(ErrorStatus.STORE_NOT_FOUND));

        newMission.setStore(store);

        return missionRepository.save(newMission);
    }

    @Override
    public MemberMission challengeMission(MissionRequestDTO.ChallengeMissionDTO request) {

        MemberMission newMemberMission = MemberMissionConverter.toMemberMission(request);
        Member member = memberRepository.findById(request.getMemberId()).orElseThrow(() -> new ErrorHandler(ErrorStatus.MEMBER_NOT_FOUND));
        Mission mission = missionRepository.findById(request.getMissionId()).orElseThrow(() -> new ErrorHandler(ErrorStatus.MISSION_NOT_FOUND));

        newMemberMission.setMember(member);
        newMemberMission.setMission(mission);

        return memberMissionRepository.save(newMemberMission);
    }
}

package umc.spring.service.MissionService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.spring.apiPayload.code.status.ErrorStatus;
import umc.spring.apiPayload.exception.handler.ErrorHandler;
import umc.spring.converter.MemberMissionConverter;
import umc.spring.converter.MissionConverter;
import umc.spring.domain.Member;
import umc.spring.domain.Mission;
import umc.spring.domain.Store;
import umc.spring.domain.mapping.MemberMission;
import umc.spring.repository.MemberRepository.MemberMissionRepository;
import umc.spring.repository.MemberRepository.MemberRepository;
import umc.spring.repository.MissionRepository.MissionRepository;
import umc.spring.repository.StoreRepository.StoreRepository;
import umc.spring.web.dto.MissionDTO.MissionRequestDTO;

import java.util.List;

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

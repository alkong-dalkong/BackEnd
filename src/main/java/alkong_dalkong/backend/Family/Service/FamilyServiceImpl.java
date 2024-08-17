package alkong_dalkong.backend.Family.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import alkong_dalkong.backend.Family.Domain.Family;
import alkong_dalkong.backend.Family.Dto.Request.CreateFamilyRequestDto;
import alkong_dalkong.backend.Family.Dto.Response.MemberResponseDto;
import alkong_dalkong.backend.Family.Dto.Response.MemberResponseElement;
import alkong_dalkong.backend.Family.Repository.FamilyRepository;
import alkong_dalkong.backend.Family.Service.Util.FamilyCodeGenerator;
import alkong_dalkong.backend.Relationship.Domain.Relationship;
import alkong_dalkong.backend.Relationship.Repository.RelationshipRepository;
import alkong_dalkong.backend.User.Domain.User;
import alkong_dalkong.backend.User.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class FamilyServiceImpl implements FamilyService {
    private final FamilyCodeGenerator codeGenerator;
    private final UserRepository userRepository;
    private final FamilyRepository familyRepository;
    private final RelationshipRepository relationshipRepository;

    @Override
    public MemberResponseDto getMembersByFamilyCode(String code) throws IllegalArgumentException {
        // 가족 코드로 가족 정보 조회
        Family family = familyRepository.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("가족 정보가 존재하지 않습니다."));

        // 가족ID로 relationship 조회
        List<Relationship> relationships = relationshipRepository.findAllByFamilyId(family.getId())
                .orElseThrow(() -> new IllegalArgumentException("가족 구성원이 존재하지 않습니다."));

        List<MemberResponseElement> members = new ArrayList<>();
        
        // 유저 정보를 이용하여 응답 생성
        for (Relationship relationship : relationships) {
            User user = relationship.getUser();
            members.add(new MemberResponseElement(user.getId(), user.getName(), user.getUserId()));
        }
        
        return new MemberResponseDto(members);
    }

    @Override
    public List<Family> getFamilyCodeByUserId(String userid) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getFamilyCodeByUserId'");
    }

    @Override
    public void createFamily(String userId, CreateFamilyRequestDto dto) throws IllegalArgumentException {
        // 가족 코드 생성
        String fcode = codeGenerator.generateCode(10);
        // 회원 정보 조회
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보가 존재하지 않습니다."));

        // 가족 정보를 DB에 등록
        Family family = familyRepository.save(Family.builder()
                .code(fcode)
                .fname(dto.getFamilyName())
                .build());

        // 가족 생성을 요청한 회원과 등록한 가족 정보를 연결
        relationshipRepository.save(Relationship.builder()
                .family(family)
                .user(user)
                .build());
    }
}

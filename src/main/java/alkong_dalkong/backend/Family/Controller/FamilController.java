package alkong_dalkong.backend.Family.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import alkong_dalkong.backend.Family.Dto.Request.CreateFamilyRequestDto;
import alkong_dalkong.backend.Family.Dto.Request.GetMembersRequestDto;
import alkong_dalkong.backend.Family.Service.FamilyService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class FamilController implements FamilyOperations {
    private final FamilyService familyService;

    @Override
    public ResponseEntity<?> createFamily(CreateFamilyRequestDto dto) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            familyService.createFamily(userId, dto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("가족 그룹이 생성되었습니다.");
    }

    @Override
    public ResponseEntity<?> getMembers(GetMembersRequestDto dto) {
        try {
            return ResponseEntity.ok(familyService.getMembersByFamilyCode(dto.getFamilyCode()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}

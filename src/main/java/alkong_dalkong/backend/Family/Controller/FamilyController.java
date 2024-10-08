package alkong_dalkong.backend.Family.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

// import alkong_dalkong.backend.Family.Dto.Request.CreateFamilyRequestDto;
import alkong_dalkong.backend.Family.Dto.Request.EnterFamilyRequestDto;
import alkong_dalkong.backend.Family.Service.FamilyService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class FamilyController implements FamilyOperations {
    private final FamilyService familyService;

    @Override
    public ResponseEntity<?> createFamily(/*CreateFamilyRequestDto dto*/) {
        
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(familyService.createFamily(/* dto */));
            // familyService.createFamily(/*dto*/);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        
    }

    @Override
    public ResponseEntity<?> getMembers(String familyCode) {
        try {
            return ResponseEntity.ok(familyService.getMembersByFamilyCode(familyCode));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> enterFamily(EnterFamilyRequestDto dto) {
        try {
            String fname = familyService.enterFamily(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(fname+"의 구성원으로 등록이 완료되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> getfamilies() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(familyService.getFamilies());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}

package alkong_dalkong.backend.Family.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import alkong_dalkong.backend.Family.Dto.Request.CreateFamilyRequestDto;
import alkong_dalkong.backend.Family.Dto.Request.EnterFamilyRequestDto;

public interface FamilyOperations {
    @PostMapping("/mypage/create-family")
    ResponseEntity<?> createFamily(@RequestBody CreateFamilyRequestDto dto);

    @GetMapping("/member-info/{familyCode}")
    ResponseEntity<?> getMembers(@PathVariable String familyCode);

    @PostMapping("/mypage/enter-family")
    ResponseEntity<?> enterFamily(@RequestBody EnterFamilyRequestDto dto);

    @GetMapping("/mypage/family-list")
    ResponseEntity<?> getfamilies();
}

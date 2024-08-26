package alkong_dalkong.backend.Medical.controller;

import alkong_dalkong.backend.Medical.dto.request.MedicalRequestDto;
import alkong_dalkong.backend.Medical.dto.request.MedicalUpdateRequestDto;
import alkong_dalkong.backend.Medical.dto.response.DetailMedicalResponseDto;
import alkong_dalkong.backend.Medical.service.MedicalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/medical")
public class MedicalController {

    @Autowired
    private MedicalService medicalService;

    @GetMapping("/{medical_id}")
    public ResponseEntity<?> getDetailMedicalInfo(@PathVariable("medical_id") Long medicalId) {

        try {
            DetailMedicalResponseDto data = medicalService.getMedicalDetail(medicalId);

            return ResponseEntity.ok().body(Map.of("code", 200, "data", data));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("code", 400, "error", "잘못된 입력값 제공"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("code", 500, "error", "예상치 못한 오류 발생"));
        }
    }

    @PostMapping
    public ResponseEntity<?> setDetailMedicalInfo(@RequestBody MedicalRequestDto requestDto) {
        try {
            Long medicalId = medicalService.setMedicalInfo(requestDto);

            return ResponseEntity.ok().body(Map.of("code", 200, "medical_id", medicalId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("code", 400, "error", "잘못된 입력값 제공"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("code", 500, "error", "예상치 못한 오류 발생"));
        }
    }

    @PutMapping("/{medical_id}")
    public ResponseEntity<?> updateMedicalInfo(@PathVariable("medical_id") Long medicalId,
                                               @RequestBody MedicalUpdateRequestDto requestDto) {
        try {
            medicalService.updateMedicalInfo(medicalId, requestDto);
            return ResponseEntity.ok().body(Map.of("code", 200));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("code", 400, "error", "잘못된 입력값 제공"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("code", 500, "error", "예상치 못한 오류 발생"));
        }
    }
}
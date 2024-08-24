package alkong_dalkong.backend.Medical.controller;

import alkong_dalkong.backend.Medical.dto.response.DetailMedicalResponseDto;
import alkong_dalkong.backend.Medical.service.DetailMedicalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/medical")
public class DetailMedicalController {

    @Autowired
    private DetailMedicalService detailMedicalService;

    @GetMapping("/{medical_id}")
    public ResponseEntity<?> getDetailMedicalInfo(@PathVariable("medical_id") Long medicalId) {

        try {
            DetailMedicalResponseDto data = detailMedicalService.getMedicalDetail(medicalId);

            return ResponseEntity.ok().body(Map.of("code", 200, "data", data));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("code", 400, "error", "잘못된 입력값 제공"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("code", 500, "error", "예상치 못한 오류 발생"));
        }
    }
}
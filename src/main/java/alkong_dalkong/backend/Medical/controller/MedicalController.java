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

    @GetMapping("/{medicalId}")
    public ResponseEntity<?> getDetailMedicalInfo(@PathVariable("medicalId") Long medicalId) {

        try {
            DetailMedicalResponseDto data = medicalService.getMedicalDetail(medicalId);

            return ResponseEntity.ok().body(Map.of("code", 200, "data", data));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("code", 400, "error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("code", 500, "error", e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> setDetailMedicalInfo(@RequestBody MedicalRequestDto requestDto) {
        try {
            Long medicalId = medicalService.setMedicalInfo(requestDto);

            return ResponseEntity.ok().body(Map.of("code", 200, "medicalId", medicalId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("code", 400, "error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("code", 500, "error", e.getMessage()));
        }
    }

    @PutMapping("/{medicalId}")
    public ResponseEntity<?> updateMedicalInfo(@PathVariable("medicalId") Long medicalId,
                                               @RequestBody MedicalUpdateRequestDto requestDto) {
        try {
            medicalService.updateMedicalInfo(medicalId, requestDto);
            return ResponseEntity.ok().body(Map.of("code", 200));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("code", 400, "error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("code", 500, "error", e.getMessage()));
        }
    }

    @DeleteMapping("/{medicalId}")
    public ResponseEntity<?> deleteMedicalInfo(@PathVariable("medicalId") Long medicalId) {
        try {
            medicalService.deleteMedicalInfo(medicalId);
            return ResponseEntity.ok().body(Map.of("code", 200));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("code", 400, "error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("code", 500, "error", e.getMessage()));
        }
    }
}
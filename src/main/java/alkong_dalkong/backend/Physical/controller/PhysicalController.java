package alkong_dalkong.backend.Physical.controller;

import alkong_dalkong.backend.Physical.dto.request.PhysicalRequestDto;
import alkong_dalkong.backend.Physical.dto.response.PhysicalResponseDto;
import alkong_dalkong.backend.Physical.service.PhysicalService;
import alkong_dalkong.backend.Physical.service.WeightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/physical")
public class PhysicalController {

    @Autowired
    private PhysicalService physicalService;

    @Autowired
    private WeightService weightService;

    @GetMapping("/{userId}")
    public ResponseEntity<?> getPhysicalInfo(@PathVariable Long userId, @RequestParam String period) {
        try {
            PhysicalResponseDto data = physicalService.getPhysicalInfo(userId, period);
            return ResponseEntity.ok().body(Map.of("code", 200, "period", period, "data", data));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("code", 400, "error", e.getMessage()));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("code", 500, "error", e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> addWeight(@RequestBody PhysicalRequestDto requestDto) {
        try {
            Long weightId = weightService.addWeight(requestDto);
            return ResponseEntity.ok().body(Map.of("code", 200, "weightId", weightId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("code", 400, "error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("code", 500, "error", e.getMessage()));
        }
    }
}

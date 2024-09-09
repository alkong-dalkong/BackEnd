package alkong_dalkong.backend.Physical.controller;

import alkong_dalkong.backend.Physical.dto.response.PhysicalResponseDto;
import alkong_dalkong.backend.Physical.service.PhysicalService;
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
}

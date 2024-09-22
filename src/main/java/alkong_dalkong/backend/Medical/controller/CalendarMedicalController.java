package alkong_dalkong.backend.Medical.controller;

import alkong_dalkong.backend.Medical.dto.response.CalendarMedicalResponseDto;
import alkong_dalkong.backend.Medical.service.CalendarMedicalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/medical")
public class CalendarMedicalController {

    @Autowired
    private CalendarMedicalService calendarMedicalService;

    @GetMapping("/calendar/{userId}/{localDate}")
    public ResponseEntity<Map<String, Object>> getMedicalCalendar(@PathVariable("userId") Long userId,
                                                                  @PathVariable("localDate") String localDate) {
        try {
            List<CalendarMedicalResponseDto> data = calendarMedicalService.getMedicalCalendar(userId, localDate);

            return ResponseEntity.ok().body(Map.of("code", 200, "data", data));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("code", 400, "error", e.getMessage()));
        } catch (DateTimeParseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("code", 402, "error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("code", 500, "error", e.getMessage()));
        }
    }
}
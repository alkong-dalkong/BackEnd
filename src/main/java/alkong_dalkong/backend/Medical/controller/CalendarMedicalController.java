package alkong_dalkong.backend.Medical.controller;

import alkong_dalkong.backend.Medical.dto.CalendarMedicalResponseDto;
import alkong_dalkong.backend.Medical.service.CalendarMedicalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/medical")
public class CalendarMedicalController {

    @Autowired
    private CalendarMedicalService calendarMedicalService;

    @GetMapping("/calendar/{user_id}/{local_date}")
    public ResponseEntity<Map<String, Object>> getMedicalCalendar(@PathVariable("user_id") Long userId,
                                                                  @PathVariable("local_date") String localDate) {
        List<CalendarMedicalResponseDto> data = calendarMedicalService.getMedicalCalendar(userId, localDate);
        return ResponseEntity.ok().body(Map.of("code", 200, "data", data));
    }
}
package alkong_dalkong.backend.Medical.controller;

import alkong_dalkong.backend.Medical.service.CalendarMedicalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
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
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
            LocalDate date = LocalDate.parse(localDate, formatter);
            LocalDateTime startOfMonth = date.withDayOfMonth(1).atStartOfDay();
            LocalDateTime endOfMonth = date.withDayOfMonth(date.lengthOfMonth()).atTime(23, 59, 59);

            List<Map<String, Object>> data = calendarMedicalService.getMedicalInfoForMonth(userId, startOfMonth, endOfMonth);

            Map<String, Object> response = new HashMap<>();
            response.put("code", 200);
            response.put("data", data);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("code", 301); // 301은 예시

            return new ResponseEntity<>(errorResponse, HttpStatus.MOVED_PERMANENTLY);
        }
    }
}
package alkong_dalkong.backend.Medical.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/medical")
public class CalendarMedicalController {

    public void getMedicalCalendar(@PathVariable("user_id") Long userId,
                                   @PathVariable("local_date") String localDate) {
        return;
    }
}
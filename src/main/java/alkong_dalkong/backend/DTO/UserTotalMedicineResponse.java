package alkong_dalkong.backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class UserTotalMedicineResponse {
    private Long medicine_id;
    private String medicine_name;
    private List<DayOfWeek> medicine_week;
    private List<LocalTime> medicine_taken_time = new ArrayList<>();
    private Long medicine_dosage;
    private String medicine_memo;
}

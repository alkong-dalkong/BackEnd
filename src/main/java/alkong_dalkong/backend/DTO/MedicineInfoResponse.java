package alkong_dalkong.backend.DTO;

import alkong_dalkong.backend.Domain.Medicine.Enum.MedicineAlarm;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
public class MedicineInfoResponse {
    private String medicine_name;
    private List<DayOfWeek> medicine_week;
    private Integer medicine_times;
    private List<LocalTime> medicine_taken_time;
    private List<LocalDate> medicine_taken_date;
    private Long medicine_dosage;
    private String medicine_memo;
    private Integer medicine_alarm;
}

package alkong_dalkong.backend.DTO;

import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class AddNewMedicineRequest {
    private String medicine_name;
    private List<DayOfWeek> medicine_week = new ArrayList<>();
    private Integer medicine_times;
    private List<LocalTime> medicine_taken_time = new ArrayList<>();
    private LocalDate medicine_start;
    private LocalDate medicine_end;
    private Long medicine_dosage;
    private String medicine_memo;
    private Integer medicine_alarm;
}

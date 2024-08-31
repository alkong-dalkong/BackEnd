package alkong_dalkong.backend.Main.MainInfo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@Data
public class CurrentMedicineInfo {
    private String medicineName;
    private List<LocalTime> times;
    private List<DayOfWeek> weekList;
}

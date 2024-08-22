package alkong_dalkong.backend.DTO;

import alkong_dalkong.backend.Domain.Medicine.Enum.TakenType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
public class MedicineInfoResponse {
    private String medicineName;
    private List<DayOfWeek> medicineWeek;
    private Integer medicineTimes;
    private List<LocalTime> medicineTakenTime;
    private LocalDate medicineEndDate;
    private Long medicineDosage;
    private TakenType medicineTakenType;
    private String medicineMemo;
    private int medicineAlarm;
}

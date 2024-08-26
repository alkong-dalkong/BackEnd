package alkong_dalkong.backend.Medicine.DTO.Response;

import alkong_dalkong.backend.Medicine.Domain.Enum.TakenType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class UserTotalMedicineResponse {
    private Long medicineId;
    private String medicineName;
    private List<DayOfWeek> medicineWeek;
    private List<LocalTime> medicineTakenTime = new ArrayList<>();
    private Long medicineDosage;
    private TakenType medicineTakenType;
    private String medicineMemo;
}

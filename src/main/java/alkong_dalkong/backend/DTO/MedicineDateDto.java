package alkong_dalkong.backend.DTO;

import alkong_dalkong.backend.Domain.Medicine.Enum.TakenType;
import lombok.Data;

@Data
public class MedicineDateDto {
    private Long medicineId;
    private Long medicineRecordId;
    private String medicineName;
    private Long medicineDosage;
    private TakenType medicineTakenType;
}

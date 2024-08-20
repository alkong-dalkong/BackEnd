package alkong_dalkong.backend.DTO;

import alkong_dalkong.backend.Domain.Medicine.Enum.MedicineTaken;
import lombok.Data;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Data
public class MedicineDateResponse {
    private Long medicine_id;
    private Long medicine_record_id;
    private String medicine_name;
    private Long medicine_dosage;
    private Map<LocalTime, MedicineTaken> medicine_taken_info = new HashMap<>();
}

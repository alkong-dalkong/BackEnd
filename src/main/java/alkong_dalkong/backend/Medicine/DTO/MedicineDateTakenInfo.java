package alkong_dalkong.backend.Medicine.DTO;

import alkong_dalkong.backend.Medicine.Domain.Enum.MedicineTaken;
import lombok.Data;

@Data
public class MedicineDateTakenInfo {
    public Long medicine_id;
    public MedicineTaken taken;
    public int index;
}

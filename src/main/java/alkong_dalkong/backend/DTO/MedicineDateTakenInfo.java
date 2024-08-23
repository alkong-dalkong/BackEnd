package alkong_dalkong.backend.DTO;

import alkong_dalkong.backend.Domain.Medicine.Enum.MedicineTaken;
import lombok.Data;

@Data
public class MedicineDateTakenInfo {
    public Long medicine_id;
    public MedicineTaken taken;
    public int index;
}

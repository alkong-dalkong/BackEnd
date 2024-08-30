package alkong_dalkong.backend.Main.MainInfo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class RecentMedicalInfo {
    private String hospitalName;
    private LocalDateTime hospitalDate;
}

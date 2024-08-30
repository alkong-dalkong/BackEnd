package alkong_dalkong.backend.Main.MainInfo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class RecentWeightInfo {
    public Long weight;
    public LocalDate date;
}

package alkong_dalkong.backend.Main;

import alkong_dalkong.backend.Main.MainInfo.CurrentMedicineInfo;
import alkong_dalkong.backend.Main.MainInfo.RecentMedicalInfo;
import alkong_dalkong.backend.Main.MainInfo.RecentWeightInfo;
import alkong_dalkong.backend.Main.MainInfo.UpcomingMedicalInfo;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserMainResponseDto {
    public UpcomingMedicalInfo upcomingMedicalInfo;
    public RecentMedicalInfo recentMedicalInfo;
    public RecentWeightInfo recentWeightInfo;
    public List<CurrentMedicineInfo> currentMedicineInfo = new ArrayList<>();
}

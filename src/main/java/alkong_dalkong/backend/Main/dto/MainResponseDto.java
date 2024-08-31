package alkong_dalkong.backend.Main.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class MainResponseDto {

    private UpcomingMedicalInfo upcomingMedicalInfo;
    private RecentMedicalInfo recentMedicalInfo;
    private List<CurrentMedicineInfo> currentMedicineInfo;

    @Data
    @AllArgsConstructor
    public static class UpcomingMedicalInfo {
        private String hospitalName;
        private LocalDateTime hospitalDate;
        private List<String> medicalPart;
    }

    @Data
    @AllArgsConstructor
    public static class RecentMedicalInfo {
        private String hospitalName;
        private LocalDateTime hospitalDate;
    }

    @Data
    @AllArgsConstructor
    public static class CurrentMedicineInfo {
        private String medicineName;
        private List<LocalTime> times;
        private List<DayOfWeek> weekList;
    }
}

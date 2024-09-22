package alkong_dalkong.backend.Main.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class MainResponseDto {

    private UpcomingMedicalInfo upcomingMedicalInfo;
    private RecentMedicalInfo recentMedicalInfo;
    private RecentWeightInfo recentWeightInfo;
    private List<CurrentMedicineInfo> currentMedicineInfo;

    @Data
    @AllArgsConstructor
    public static class UpcomingMedicalInfo {
        private String hospitalName;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime hospitalDate;

        private List<String> medicalPart;
    }

    @Data
    @AllArgsConstructor
    public static class RecentMedicalInfo {
        private String hospitalName;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime hospitalDate;
    }

    @Data
    @AllArgsConstructor
    public static class RecentWeightInfo {
        private float weight;
        private LocalDate date;
    }

    @Data
    @AllArgsConstructor
    public static class CurrentMedicineInfo {
        private String medicineName;
        private List<LocalTime> times;
        private List<DayOfWeek> weekList;
    }
}

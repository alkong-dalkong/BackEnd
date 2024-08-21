package alkong_dalkong.backend.Medical.dto;

import java.time.LocalDateTime;
import java.util.List;

public class CalendarMedicalResponseDto {

    private Long medicalId;
    private String hospitalName;
    private LocalDateTime hospitalDate;
    private List<String> medicalPart;

    public CalendarMedicalResponseDto(Long medicalId, String hospitalName, LocalDateTime hospitalDate, List<String> medicalPart) {
        this.medicalId = medicalId;
        this.hospitalName = hospitalName;
        this.hospitalDate = hospitalDate;
        this.medicalPart = medicalPart;
    }
}

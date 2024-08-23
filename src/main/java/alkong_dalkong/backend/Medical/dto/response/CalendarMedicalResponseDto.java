package alkong_dalkong.backend.Medical.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
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
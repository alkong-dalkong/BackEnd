package alkong_dalkong.backend.Medical.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class CalendarMedicalResponseDto {

    private Long medicalId;
    private String hospitalName;
    private LocalDateTime hospitalDate;
    private List<String> medicalPart;
}

package alkong_dalkong.backend.Medical.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class DetailMedicalResponseDto {

    private Long medicalId;
    private String hospitalName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime hospitalDate;

    private List<String> medicalPart;
    private String medicalMemo;
    private int medicalAlarm;
}
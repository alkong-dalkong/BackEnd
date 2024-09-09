package alkong_dalkong.backend.Physical.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhysicalRequestDto {
    private Long physicalId;
    private float weight;
    private LocalDate createdAt;
}
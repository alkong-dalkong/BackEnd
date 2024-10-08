package alkong_dalkong.backend.Family.Dto.Response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateFamilyResponseDto {
    private String familyName;
    private String familyCode;
}

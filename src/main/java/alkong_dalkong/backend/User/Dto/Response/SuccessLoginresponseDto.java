package alkong_dalkong.backend.User.Dto.Response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SuccessLoginresponseDto {
    private Long userId;
    private String id;
    private String name;
    private String familyCode;
}

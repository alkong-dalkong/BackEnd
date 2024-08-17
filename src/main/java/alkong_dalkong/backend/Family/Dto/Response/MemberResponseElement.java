package alkong_dalkong.backend.Family.Dto.Response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberResponseElement {
    private Long userIdKey;
    private String name;
    private String userId;
}

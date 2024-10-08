package alkong_dalkong.backend.Family.Dto.Response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FamilyResponseElement {
    private String familyCode;
    private String familyName;
    private List<MemberResponseElement> members;
}

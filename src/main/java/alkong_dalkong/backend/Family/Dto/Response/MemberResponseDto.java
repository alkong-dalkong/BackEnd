package alkong_dalkong.backend.Family.Dto.Response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberResponseDto {
    private List<MemberResponseElement> members;
}

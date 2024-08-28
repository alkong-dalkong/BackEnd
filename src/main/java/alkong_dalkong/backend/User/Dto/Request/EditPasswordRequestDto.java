package alkong_dalkong.backend.User.Dto.Request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EditPasswordRequestDto {
    private String password;
    private String newPassword;
}

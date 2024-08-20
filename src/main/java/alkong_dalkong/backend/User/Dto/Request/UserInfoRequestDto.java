package alkong_dalkong.backend.User.Dto.Request;

import java.time.LocalDate;

import alkong_dalkong.backend.User.Domain.Gender;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserInfoRequestDto {
    @NotEmpty(message = "이름이 입력되지 않았습니다.")
    private String name;

    @NotEmpty(message = "전화번호가 입력되지 않았습니다.")
    private String phoneNumber;

    @NotNull(message = "생일이 입력되지 않았습니다.")
    private LocalDate birth;

    @NotNull(message = "성별이 입력되지 않았습니다.")
    private Gender gender;
}

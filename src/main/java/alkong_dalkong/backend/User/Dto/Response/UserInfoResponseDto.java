package alkong_dalkong.backend.User.Dto.Response;

import java.time.LocalDate;

import alkong_dalkong.backend.User.Domain.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserInfoResponseDto {
    private String name;
    private String phoneNumber;
    private LocalDate birth;
    private Gender gender;
}

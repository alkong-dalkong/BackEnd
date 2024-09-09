package alkong_dalkong.backend.Physical.util;

import java.time.LocalDate;
import java.time.Period;

public class AgeRangeUtils {

    // 유저의 생년월일로 만나이 계산
    public static int calculateAge(LocalDate birthDate, LocalDate currentDate) {
        if (birthDate != null && currentDate != null) {
            // 연도 차이를 먼저 계산
            int age = Period.between(birthDate, currentDate).getYears();

            // 생일이 지났는지 여부를 확인 (생일이 지나지 않았다면 나이를 1 줄임)
            if (currentDate.getMonthValue() < birthDate.getMonthValue() ||
                    (currentDate.getMonthValue() == birthDate.getMonthValue() && currentDate.getDayOfMonth() < birthDate.getDayOfMonth())) {
                age--;
            }
            return age;
        }
        return 0;
    }
}
package alkong_dalkong.backend.Family.Service.Util;

import java.util.Random;

import org.springframework.stereotype.Service;

import alkong_dalkong.backend.Family.Repository.FamilyRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FamilyCodeGenerator {
    private final FamilyRepository familyRepository;

    public String generateCode(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();

        String code;
        do {
            StringBuilder stringBuilder = new StringBuilder(length);
            for (int i = 0; i < length; i++) {
                int randomIndex = random.nextInt(characters.length());
                stringBuilder.append(characters.charAt(randomIndex));
            }
            code = stringBuilder.toString();
        } while (familyRepository.existsByCode(code)); // 코드가 중복되는 경우 다시 생성

        return code;
    }
}

package alkong_dalkong.backend.Medical.service;

import alkong_dalkong.backend.Medical.dto.response.DetailMedicalResponseDto;
import alkong_dalkong.backend.Medical.entity.MedicalInfo;
import alkong_dalkong.backend.Medical.repository.MedicalInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class MedicalService {

    @Autowired
    private MedicalInfoRepository medicalInfoRepository;

    /* 진료 정보 get */
    public DetailMedicalResponseDto getMedicalDetail(Long medicalId) {
        Optional<MedicalInfo> optionalMedicalInfo = medicalInfoRepository.findById(medicalId);

        if(optionalMedicalInfo.isPresent()) {
            MedicalInfo medicalInfo = optionalMedicalInfo.get();

            int medicalAlarmIndex = calculateAlarmIndex(medicalInfo.getHospitalDate(), medicalInfo.getMedicalAlarm());

            return new DetailMedicalResponseDto(
                    medicalInfo.getMedicalId(),
                    medicalInfo.getHospitalName(),
                    medicalInfo.getHospitalDate(),
                    medicalInfo.getMedicalPart(),
                    medicalInfo.getMedicalMemo(),
                    medicalAlarmIndex
            );
        } else {
            return null;
        }

    }

    /* 알람 인덱스 계산 */
    private int calculateAlarmIndex(LocalDateTime hospitalDate, LocalDateTime medicalAlarm) {
        if (medicalAlarm == null) {
            return 5;
        }

        long minutesDiff = Duration.between(medicalAlarm, hospitalDate).toMinutes();

        if(minutesDiff == Duration.ofDays(7).toMinutes()) {
            return 0;
        } else if (minutesDiff == Duration.ofHours(24).toMinutes()) {
            return 1;
        } else if (minutesDiff == Duration.ofHours(12).toMinutes()) {
            return 2;
        } else if (minutesDiff == Duration.ofHours(1).toMinutes()) {
            return 3;
        } else if (minutesDiff == Duration.ofMinutes(30).toMinutes()) {
            return 4;
        }

        return 5; // 모두 해당하지 않는 경우 -> 추후 예외처리 필요
    }

    /* 알람 시간 계산 */
    private LocalDateTime calculateAlarmDate(LocalDateTime hospitalDate, int medicalAlarmIndex) {
        return switch (medicalAlarmIndex) {
            case 0 -> hospitalDate.minusDays(7);
            case 1 -> hospitalDate.minusHours(24);
            case 2 -> hospitalDate.minusHours(12);
            case 3 -> hospitalDate.minusHours(1);
            case 4 -> hospitalDate.minusMinutes(30);
            default -> null; // case 5
        };
    }
}

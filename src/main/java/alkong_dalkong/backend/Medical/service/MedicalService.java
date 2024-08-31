package alkong_dalkong.backend.Medical.service;

import alkong_dalkong.backend.Medical.dto.request.MedicalRequestDto;
import alkong_dalkong.backend.Medical.dto.request.MedicalUpdateRequestDto;
import alkong_dalkong.backend.Medical.dto.response.DetailMedicalResponseDto;
import alkong_dalkong.backend.Medical.entity.MedicalInfo;
import alkong_dalkong.backend.Medical.entity.Users;
import alkong_dalkong.backend.Medical.repository.MedicalInfoRepository;
import alkong_dalkong.backend.Medical.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class MedicalService {

    @Autowired
    private MedicalInfoRepository medicalInfoRepository;

    @Autowired
    private UsersRepository usersRepository;

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

    /* 진료 정보 post */
    public Long setMedicalInfo(MedicalRequestDto requestDto) {
        Users user = usersRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        LocalDateTime medicalAlarm = calculateAlarmDate(requestDto.getHospitalDate(), requestDto.getMedicalAlarm());

        MedicalInfo medicalInfo = new MedicalInfo();
        medicalInfo.setUser(user);
        medicalInfo.setHospitalName(requestDto.getHospitalName());
        medicalInfo.setHospitalDate(requestDto.getHospitalDate());
        medicalInfo.setMedicalPart(requestDto.getMedicalPart());
        medicalInfo.setMedicalMemo(requestDto.getMedicalMemo());
        medicalInfo.setMedicalAlarm(medicalAlarm);

        medicalInfo = medicalInfoRepository.save(medicalInfo);

        return medicalInfo.getMedicalId();
    }

    /* 진료 정보 업데이트 (put) */
    public void updateMedicalInfo(Long medicalId, MedicalUpdateRequestDto requestDto) {
        Optional<MedicalInfo> optionalMedicalInfo = medicalInfoRepository.findById(medicalId);
        if (optionalMedicalInfo.isPresent()) {
            MedicalInfo medicalInfo = optionalMedicalInfo.get();

            // 필드별로 선택적 업데이트
            if (requestDto.getHospitalName() != null) {
                medicalInfo.setHospitalName(requestDto.getHospitalName());
            }

            if (requestDto.getHospitalDate() != null) {
                medicalInfo.setHospitalDate(requestDto.getHospitalDate());
            }

            if (requestDto.getMedicalPart() != null) {
                medicalInfo.setMedicalPart(requestDto.getMedicalPart());
            }

            if (requestDto.getMedicalMemo() != null) {
                medicalInfo.setMedicalMemo(requestDto.getMedicalMemo());
            }

            if (requestDto.getMedicalAlarm() != null) {
                LocalDateTime medicalAlarm = calculateAlarmDate(requestDto.getHospitalDate(), requestDto.getMedicalAlarm());
                medicalInfo.setMedicalAlarm(medicalAlarm);
            }

            medicalInfoRepository.save(medicalInfo);
        } else {
            throw new IllegalArgumentException("medicalId: " + medicalId + "가 존재하지 않습니다.");
        }
    }

    /* 진료 정보 delete */
    public void deleteMedicalInfo(Long medicalId) {
        // medical_id에 해당하는 진료 정보가 존재하는지 확인
        if (medicalInfoRepository.existsById(medicalId)) {
            medicalInfoRepository.deleteById(medicalId);
        } else {
            throw new IllegalArgumentException("medicalId: " + medicalId + "가 존재하지 않습니다.");
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

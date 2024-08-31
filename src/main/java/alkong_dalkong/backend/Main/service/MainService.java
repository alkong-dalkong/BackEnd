package alkong_dalkong.backend.Main.service;

import alkong_dalkong.backend.Main.dto.MainResponseDto;
import alkong_dalkong.backend.Medical.entity.MedicalInfo;
import alkong_dalkong.backend.Medical.repository.MedicalInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class MainService {

    @Autowired
    private MedicalInfoRepository medicalInfoRepository;

    public MainResponseDto getMainInfo(Long userId, LocalDateTime localDate) {
        // 가장 최근 지난 병원 내원 정보
        Optional<MedicalInfo> recentMedicalInfoOpt = medicalInfoRepository.findTopByUserUserIdAndHospitalDateLessThanEqualOrderByHospitalDateDesc(userId, localDate);
        MainResponseDto.RecentMedicalInfo recentMedicalInfo = null;

        if (recentMedicalInfoOpt.isPresent()) {
            MedicalInfo recentMedicalInfoEntity = recentMedicalInfoOpt.get();
            recentMedicalInfo = new MainResponseDto.RecentMedicalInfo(
                    recentMedicalInfoEntity.getHospitalName(),
                    recentMedicalInfoEntity.getHospitalDate()
            );
        }

        return new MainResponseDto(upcomingMedicalInfo, recentMedicalInfo);
    }
}
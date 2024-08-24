package alkong_dalkong.backend.Medical.service;

import alkong_dalkong.backend.Medical.dto.response.DetailMedicalResponseDto;
import alkong_dalkong.backend.Medical.entity.MedicalInfo;
import alkong_dalkong.backend.Medical.repository.MedicalInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;

public class DetailMedicalService {

    @Autowired
    private MedicalInfoRepository medicalInfoRepository;

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

    private int calculateAlarmIndex(LocalDateTime hospitalDate, LocalDateTime medicalAlarm) {
    }
}

package alkong_dalkong.backend.Medical.service;

import alkong_dalkong.backend.Medical.dto.response.CalendarMedicalResponseDto;
import alkong_dalkong.backend.Medical.entity.MedicalInfo;
import alkong_dalkong.backend.Medical.repository.MedicalInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CalendarMedicalService {

    @Autowired
    private MedicalInfoRepository medicalInfoRepository;

    public List<CalendarMedicalResponseDto> getMedicalCalendar(Long userId, String localDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        YearMonth yearMonth = YearMonth.parse(localDate, formatter);
        LocalDateTime startDate = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime endDate = yearMonth.atEndOfMonth().atTime(23, 59, 59);

        List<MedicalInfo> medicalInfos = medicalInfoRepository.findByUserUserIdAndHospitalDateBetween(userId, startDate, endDate);

        return medicalInfos.stream().map(medicalInfo -> new CalendarMedicalResponseDto(
                medicalInfo.getMedicalId(),
                medicalInfo.getHospitalName(),
                medicalInfo.getHospitalDate(),
                medicalInfo.getMedicalPart()
        )).collect(Collectors.toList());
    }
}

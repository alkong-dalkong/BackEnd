package alkong_dalkong.backend.Medical.service;

import alkong_dalkong.backend.Medical.dto.CalendarMedicalResponseDto;
import alkong_dalkong.backend.Medical.entity.MedicalInfo;
import alkong_dalkong.backend.Medical.repository.MedicalInfoCalendarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CalendarMedicalService {

    @Autowired
    private MedicalInfoCalendarRepository medicalInfoCalendarRepository;

//    public List<Map<String, Object>> getMedicalInfoForMonth(Long userId, LocalDateTime startOfMonth, LocalDateTime endOfMonth) {
//        List<MedicalInfo> medicalInfos = medicalInfoCalendarRepository.findByUserUserIdAndHospitalDateBetween(userId, startOfMonth, endOfMonth);
//
//        List<Map<String, Object>> data = new ArrayList<>();
//        for (MedicalInfo info : medicalInfos) {
//            Map<String, Object> item = new HashMap<>();
//            item.put("medical_id", info.getMedicalId());
//            item.put("hospital_name", info.getHospitalName());
//            item.put("hospital_date", info.getHospitalDate());
//            item.put("medical_part", info.getMedicalPart());
//            data.add(item);
//        }
//
//        return data;
//    }

    public List<CalendarMedicalResponseDto> getMedicalCalendar(Long userId, String localDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        YearMonth yearMonth = YearMonth.parse(localDate, formatter);
        LocalDateTime startDate = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime endDate = yearMonth.atEndOfMonth().atTime(23, 59, 59);

        List<MedicalInfo> medicalInfos = medicalInfoCalendarRepository.findByUserUserIdAndHospitalDateBetween(userId, startDate, endDate);

        return medicalInfos.stream().map(medicalInfo -> new CalendarMedicalResponseDto(
                medicalInfo.getMedicalId(),
                medicalInfo.getHospitalName(),
                medicalInfo.getHospitalDate(),
                medicalInfo.getMedicalPart()
        )).collect(Collectors.toList());
    }
}

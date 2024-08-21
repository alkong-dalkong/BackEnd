package alkong_dalkong.backend.Medical.service;

import alkong_dalkong.backend.Medical.entity.MedicalInfo;
import alkong_dalkong.backend.Medical.repository.MedicalInfoCalendarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CalendarMedicalService {

    @Autowired
    private MedicalInfoCalendarRepository medicalInfoCalendarRepository;

    public List<Map<String, Object>> getMedicalInfoForMonth(Long userId, LocalDateTime startOfMonth, LocalDateTime endOfMonth) {
        List<MedicalInfo> medicalInfos = medicalInfoCalendarRepository.findByUserUserIdAndHospitalDateBetween(userId, startOfMonth, endOfMonth);

        List<Map<String, Object>> data = new ArrayList<>();
        for (MedicalInfo info : medicalInfos) {
            Map<String, Object> item = new HashMap<>();
            item.put("medical_id", info.getMedicalId());
            item.put("hospital_name", info.getHospitalName());
            item.put("hospital_date", info.getHospitalDate());
            item.put("medical_part", info.getMedicalPart());
            data.add(item);
        }

        return data;
    }
}

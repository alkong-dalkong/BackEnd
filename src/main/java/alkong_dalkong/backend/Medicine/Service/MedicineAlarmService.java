package alkong_dalkong.backend.Medicine.Service;

import alkong_dalkong.backend.Medicine.Domain.Enum.MedicineTaken;
import alkong_dalkong.backend.Medicine.Domain.MedicineAlarm;
import alkong_dalkong.backend.Medicine.Domain.MedicineRecord;
import alkong_dalkong.backend.Medicine.Domain.MedicineRelation;
import alkong_dalkong.backend.Medicine.Repository.MedicineAlarmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MedicineAlarmService {
    private final MedicineAlarmRepository medicineAlarmRepository;

    // 약 알람 저장
    public void saveMedicineAlarm(MedicineAlarm medicineAlarm){
        medicineAlarmRepository.save(medicineAlarm);
    }

    public void saveNewMedicineAlarm(MedicineRelation medicineRelation, LocalDateTime dateTime){
        // 시간 설정
        switch(medicineRelation.getMedicineAlarm()){
            case 0 -> dateTime = dateTime.minusHours(1);
            case 1 -> dateTime = dateTime.minusMinutes(30);
            case 2 -> dateTime = dateTime.minusMinutes(10);
        }

        MedicineAlarm medicineAlarm = new MedicineAlarm();
        medicineAlarm.setMedicineAlarm(medicineRelation, dateTime);

        medicineAlarmRepository.save(medicineAlarm);
    }

    // 약 알람 시간 저장
    public void createMedicineAlarmByMedicineRelation(MedicineRelation medicineRelation, List<LocalDate> totalDate){
        if(medicineRelation.getMedicineAlarm() == 3)
            return;

        for(LocalDate localDate : totalDate){
            LocalDateTime dateTime;

            if(medicineRelation.getMedicineBreakfast() != null){
                dateTime = LocalDateTime.of(localDate, medicineRelation.getMedicineBreakfast());
                saveNewMedicineAlarm(medicineRelation, dateTime);
            }
            if(medicineRelation.getMedicineLunch() != null){
                dateTime = LocalDateTime.of(localDate, medicineRelation.getMedicineLunch());
                saveNewMedicineAlarm(medicineRelation, dateTime);
            }
            if(medicineRelation.getMedicineDinner() != null){
                dateTime = LocalDateTime.of(localDate, medicineRelation.getMedicineDinner());
                saveNewMedicineAlarm(medicineRelation, dateTime);
            }
        }
    }

    // 약 알람 기록 전부 삭제
    public void deleteAllMedicineAlarm(Long medicineRelationId){
        List<MedicineAlarm> medicineAlarmList = medicineAlarmRepository.findByMedicineRelationId(medicineRelationId);
        medicineAlarmRepository.deleteAll(medicineAlarmList);
    }
}

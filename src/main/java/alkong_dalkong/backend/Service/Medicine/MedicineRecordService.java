package alkong_dalkong.backend.Service.Medicine;

import alkong_dalkong.backend.Domain.Medicine.Enum.MedicineTaken;
import alkong_dalkong.backend.Domain.Medicine.MedicineRecord;
import alkong_dalkong.backend.Domain.Medicine.MedicineRelation;
import alkong_dalkong.backend.Repository.Medicine.MedicineRecordRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MedicineRecordService {
    private final MedicineRecordRepository medicineRecordRepository;

    // 약 기록 추가 저장
    public void saveMedicineRecord(MedicineRecord medicineRecord){
        medicineRecordRepository.save(medicineRecord);
    }

    // 특정 날짜의 약 복용 기록
    public MedicineRecord dateMedicineRecord(LocalDate date, Long medicine_relation_id){
        List<MedicineRecord> medicineRecordList = medicineRecordRepository.findByTakenDate(date);
        for(MedicineRecord medicineRecord : medicineRecordList){
            if(medicineRecord.getMedicineRelation().getId().equals(medicine_relation_id)){
                return medicineRecord;
            }
        }
        throw new IllegalStateException("헤당 날짜에 일치하는 약 정보가 없습니다.");
    }

    // 특정 약의 모든 복용 날짜
    public List<LocalDate> FindAllDateByMedicine(Long medicine_relation_id){
        List<MedicineRecord> medicineRecordList = medicineRecordRepository.findByMedicineRelationId(medicine_relation_id);
        List<LocalDate> dateList = new ArrayList<>();

        for(MedicineRecord medicineRecord : medicineRecordList){
            dateList.add(medicineRecord.getTakenDate());
        }

        return dateList;
    }

    // 특정 약의 복용 기록
    public void updateMedicineTaken(Long medicine_record_id, Integer time_num, Integer taken_num){
        Optional<MedicineRecord> findMedicineRecord = medicineRecordRepository.findById(medicine_record_id);
        MedicineRecord medicineRecord = findMedicineRecord.orElseThrow(()->new IllegalStateException("Medicine Record ID가 존재하지 않습니다."));

        MedicineTaken taken;
        if(taken_num == 0){
            taken = MedicineTaken.NOT_TAKEN;
        }
        else if(taken_num == 1){
            taken = MedicineTaken.TAKEN;
        }
        else{
            throw new IllegalStateException("복용 정보가 올바르지 않습니다.");
        }

        if(time_num == 0){
            medicineRecord.changeBreakfastTaken(taken);
        }
        else if(time_num == 1){
            medicineRecord.changeLunchTaken(taken);
        }
        else if(time_num == 2){
            medicineRecord.changeDinnerTaken(taken);
        }
        else{
            throw new IllegalStateException("시간 정보가 올바르지 않습니다.");
        }
    }

    public void removeMedicineRecord(MedicineRelation removeMedicine){
        List<MedicineRecord> medicineRecordList = medicineRecordRepository.findByMedicineRelationId(removeMedicine.getId());
        if (!medicineRecordList.isEmpty()) {
            medicineRecordRepository.deleteAll(medicineRecordList);
        }
    }
}

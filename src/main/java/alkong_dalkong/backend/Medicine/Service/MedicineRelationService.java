package alkong_dalkong.backend.Medicine.Service;

import alkong_dalkong.backend.Medicine.Domain.Enum.MedicineTaken;
import alkong_dalkong.backend.Medicine.Domain.MedicineRecord;
import alkong_dalkong.backend.Medicine.Domain.MedicineRelation;
import alkong_dalkong.backend.Medicine.Repository.MedicineRecordRepository;
import alkong_dalkong.backend.Medicine.Repository.MedicineRelationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MedicineRelationService {
    private final MedicineRelationRepository medicinerelationrepository;
    private final MedicineRecordRepository medicineRecordRepository;
    private final MedicineRelationRepository medicineRelationRepository;
    private final MedicineRecordService medicineRecordService;

    // 약 정보 저장
    public void saveMedicineRelation(MedicineRelation newMedicineRelation){
        medicinerelationrepository.save(newMedicineRelation);
    }

    // 약 정보를 사용해 약 기록 테이블 생성
    public void createNewMedicine(MedicineRelation medicineRelation, List<LocalDate> totalDate){
        for(LocalDate localDate : totalDate){
            // 중복 검사
            if(medicineRecordRepository.findByTakenDateAndMedicineRelationId(localDate, medicineRelation.getId()) != null)
                continue;

            MedicineRecord medicineRecord =
                    MedicineRecord.createMedicineRecord(localDate, medicineRelation);
            if(medicineRelation.getMedicineBreakfast() != null){
                medicineRecord.changeBreakfastTaken(MedicineTaken.NOT_TAKEN);
            }
            if(medicineRelation.getMedicineLunch() != null){
                medicineRecord.changeLunchTaken(MedicineTaken.NOT_TAKEN);
            }
            if(medicineRelation.getMedicineDinner() != null){
                medicineRecord.changeDinnerTaken(MedicineTaken.NOT_TAKEN);
            }

            medicineRecordRepository.save(medicineRecord);
        }
    }

    // 사용자가 복용하는 모든 약 정보
    public List<MedicineRelation> FindAllUserMedicine(Long user_id){
        return medicineRelationRepository.findByMedicineUserId(user_id);
    }

    public MedicineRelation FindUserMedicine(Long user_id, Long medicine_id){
        List<MedicineRelation> medicineRelationList = FindAllUserMedicine(user_id);

        // 사용자가 소유하고 있는 약 검색
        for(MedicineRelation medicineRelation : medicineRelationList){
            if(medicineRelation.getMedicine().getId().equals(medicine_id)){
                return medicineRelation;
            }
        }

        throw new IllegalStateException("사용자가 약 정보를 가지고 있지 않습니다.");
    }


    // 복용 가능해야 하는 모든 날짜
    public List<LocalDate> countAllDates(LocalDate startDate, LocalDate endDate, List<DayOfWeek> weekList){
        // 복용 기한이 무제한인 경우
        LocalDate lastDate = endDate;
        LocalDate infiniteDate = LocalDate.of(9999, 12, 31);
        if(lastDate.isEqual(infiniteDate)){
            lastDate = startDate.plusMonths(1);
        }

        List<LocalDate> resultList = new ArrayList<>();
        for (LocalDate date = startDate; !date.isAfter(lastDate); date = date.plusDays(1)) {
            if(weekList.contains(date.getDayOfWeek())){
                resultList.add(date);
            }
        }

        return resultList;
    }

    // 무기한 약 복용 기록 추가
    public void addMedicineInfinite(MedicineRelation medicineRelation, LocalDate date){
        List<DayOfWeek> weekList = medicineRelation.possibleWeek();
        List<LocalDate> possibleList = countAllDates(date, medicineRelation.getTakenEndDate(), weekList);

        createNewMedicine(medicineRelation, possibleList);
    }

    // 약 삭제
    public void removeMedicineRelation(Long userId, Long medicineID){
        MedicineRelation removeMedicine = FindUserMedicine(userId, medicineID);
        medicineRecordService.removeMedicineRecord(removeMedicine);
        medicineRelationRepository.delete(removeMedicine);
    }
}

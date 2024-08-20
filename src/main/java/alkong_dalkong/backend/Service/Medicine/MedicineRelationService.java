package alkong_dalkong.backend.Service.Medicine;

import alkong_dalkong.backend.Domain.Medicine.Enum.MedicineTaken;
import alkong_dalkong.backend.Domain.Medicine.Medicine;
import alkong_dalkong.backend.Domain.Medicine.MedicineRecord;
import alkong_dalkong.backend.Domain.Medicine.MedicineRelation;
import alkong_dalkong.backend.Domain.Medicine.MedicineUser;
import alkong_dalkong.backend.Repository.Medicine.MedicineRecordRepository;
import alkong_dalkong.backend.Repository.Medicine.MedicineRelationRepository;
import alkong_dalkong.backend.Repository.Medicine.MedicineUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MedicineRelationService {
    private final MedicineRelationRepository medicinerelationrepository;
    private final MedicineUserRepository medicineUserRepository;
    private final MedicineRecordRepository medicineRecordRepository;
    private final MedicineRelationRepository medicineRelationRepository;

    // 약 정보 저장
    public void saveMedicineRelation(MedicineRelation newMedicineRelation){
        medicinerelationrepository.save(newMedicineRelation);
    }

    // 약 정보를 사용해 약 기록 테이블 생성
    public void createNewMedicine(MedicineRelation medicineRelation, List<LocalDate> totalDate){
        for(LocalDate localDate : totalDate){
            MedicineRecord medicineRecord =
                    MedicineRecord.createMedicineRecord(localDate, medicineRelation);

            if(medicineRelation.getMedicineBreakfast() != null){
                medicineRecord.changeBreakfastTaken(MedicineTaken.NOT_YET);
            }
            if(medicineRelation.getMedicineLunch() != null){
                medicineRecord.changeLunchTaken(MedicineTaken.NOT_YET);
            }
            if(medicineRelation.getMedicineDinner() != null){
                medicineRecord.changeDinnerTaken(MedicineTaken.NOT_YET);
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
}

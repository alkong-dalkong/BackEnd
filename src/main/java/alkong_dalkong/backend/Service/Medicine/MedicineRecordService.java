package alkong_dalkong.backend.Service.Medicine;

import alkong_dalkong.backend.Domain.Medicine.MedicineRecord;
import alkong_dalkong.backend.Repository.Medicine.MedicineRecordRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class MedicineRecordService {
    private final MedicineRecordRepository medicineRecordRepository;

    public void saveMedicineRecord(MedicineRecord medicineRecord){
        medicineRecordRepository.save(medicineRecord);
    }
}

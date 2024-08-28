package alkong_dalkong.backend.Medicine.Service;

import alkong_dalkong.backend.Medicine.Domain.Medicine;
import alkong_dalkong.backend.Medicine.Repository.MedicineRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MedicineService {
    private final MedicineRepository medicinerepository;

    // 약 정보 저장
    public void saveMedicine( Medicine newMedicine){
        medicinerepository.save(newMedicine);
    }

    public void changeMedicineName(Long medicineId, String newName){
        Optional<Medicine> findMedicine = medicinerepository.findById(medicineId);
        Medicine medicine = findMedicine.orElseThrow(()->new IllegalStateException("유효한 약 번호가 없습니다"));
        medicine.changeName(newName);
    }
}

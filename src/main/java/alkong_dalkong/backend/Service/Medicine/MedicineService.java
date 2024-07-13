package alkong_dalkong.backend.Service.Medicine;

import alkong_dalkong.backend.Domain.Medicine.Medicine;
import alkong_dalkong.backend.Repository.Medicine.MedicineRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class MedicineService {
    private final MedicineRepository medicinerepository;

    public void saveMedicine(Medicine medicine){
        medicinerepository.save(medicine);
    }
}

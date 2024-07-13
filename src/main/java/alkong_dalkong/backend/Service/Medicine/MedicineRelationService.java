package alkong_dalkong.backend.Service.Medicine;

import alkong_dalkong.backend.Domain.Medicine.MedicineRelation;
import alkong_dalkong.backend.Repository.Medicine.MedicineRelationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class MedicineRelationService {
    private final MedicineRelationRepository medicinerelationrepository;

    public void saveMedicineRelation(MedicineRelation medicineRelation){
        medicinerelationrepository.save(medicineRelation);
    }
}

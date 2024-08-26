package alkong_dalkong.backend.Repository.Medicine;

import alkong_dalkong.backend.Domain.Medicine.MedicineRelation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicineRelationRepository extends JpaRepository<MedicineRelation, Long> {
    List<MedicineRelation> findByMedicineUserId(Long medicineUserId);
    MedicineRelation findByMedicineIdAndMedicineUserId(Long medicineId, Long medicineUserId);
}

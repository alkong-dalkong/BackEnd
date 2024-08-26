package alkong_dalkong.backend.Medicine.Repository;

import alkong_dalkong.backend.Medicine.Domain.MedicineRelation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicineRelationRepository extends JpaRepository<MedicineRelation, Long> {
    List<MedicineRelation> findByMedicineUserId(Long medicineUserId);
    MedicineRelation findByMedicineIdAndMedicineUserId(Long medicineId, Long medicineUserId);
}

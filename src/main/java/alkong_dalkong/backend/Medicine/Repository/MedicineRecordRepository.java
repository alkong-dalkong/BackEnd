package alkong_dalkong.backend.Medicine.Repository;

import alkong_dalkong.backend.Medicine.Domain.MedicineRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MedicineRecordRepository extends JpaRepository<MedicineRecord, Long> {
    List<MedicineRecord> findByTakenDate(LocalDate date);
    List<MedicineRecord> findByMedicineRelationId(Long medicineRelationId);
    MedicineRecord findByTakenDateAndMedicineRelationId(LocalDate date, Long medicineRelationId);
}

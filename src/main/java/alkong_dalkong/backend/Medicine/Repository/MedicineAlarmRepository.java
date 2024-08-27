package alkong_dalkong.backend.Medicine.Repository;

import alkong_dalkong.backend.Medicine.Domain.MedicineAlarm;
import alkong_dalkong.backend.Medicine.Domain.MedicineRecord;
import alkong_dalkong.backend.Medicine.Domain.MedicineRelation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MedicineAlarmRepository extends JpaRepository<MedicineAlarm, Long> {
    MedicineAlarm findByAlarmDateTimeAndMedicineRelationId(LocalDateTime datetime, Long medicineRelationId);
    List<MedicineAlarm> findByMedicineRelationId(Long medicineRelationId);
}

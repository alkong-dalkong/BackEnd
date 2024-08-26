package alkong_dalkong.backend.Medicine.Repository;

import alkong_dalkong.backend.Medicine.Domain.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicineRepository extends JpaRepository<Medicine, Long> {
}

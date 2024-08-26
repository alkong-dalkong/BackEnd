package alkong_dalkong.backend.Medicine.Repository;

import alkong_dalkong.backend.Medicine.Domain.MedicineUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicineUserRepository extends JpaRepository<MedicineUser, Long> {
}

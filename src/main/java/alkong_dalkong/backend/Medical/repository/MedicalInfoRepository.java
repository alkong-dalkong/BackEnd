package alkong_dalkong.backend.Medical.repository;

import alkong_dalkong.backend.Medical.entity.MedicalInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MedicalInfoRepository extends JpaRepository<MedicalInfo, Long> {
    List<MedicalInfo> findByUserUserIdAndHospitalDateBetween(Long userId, LocalDateTime startDate, LocalDateTime endDate);
}

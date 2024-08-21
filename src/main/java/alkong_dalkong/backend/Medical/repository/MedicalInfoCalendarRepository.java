package alkong_dalkong.backend.Medical.repository;

import alkong_dalkong.backend.Medical.entity.MedicalInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MedicalInfoCalendarRepository extends JpaRepository<MedicalInfo, Long> {
    List<MedicalInfo> findByUserIdAndHospitalDateBetween(Long userId, LocalDateTime startDate, LocalDateTime endDate);
}

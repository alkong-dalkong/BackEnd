package alkong_dalkong.backend.Medical.repository;

import alkong_dalkong.backend.Medical.entity.MedicalInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MedicalInfoRepository extends JpaRepository<MedicalInfo, Long> {

    // 주어진 시간 사이의 병원 내원 정보
    List<MedicalInfo> findByUserUserIdAndHospitalDateBetween(Long userId, LocalDateTime startDate, LocalDateTime endDate);

    // 최근 병원 내원 일정
    Optional<MedicalInfo> findTopByUserUserIdAndHospitalDateLessThanEqualOrderByHospitalDateDesc(Long userId, LocalDateTime localDate);

    // 다가오는 병원 내원 일정
    Optional<MedicalInfo> findTopByUserUserIdAndHospitalDateGreaterThanEqualOrderByHospitalDateAsc(Long userId, LocalDateTime localDate);
}

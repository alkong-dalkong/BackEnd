package alkong_dalkong.backend.Physical.repository;

import alkong_dalkong.backend.Physical.entity.PhysicalInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PhysicalInfoRepository extends JpaRepository<PhysicalInfo, Long> {
    Optional<PhysicalInfo> findByUserUserId(Long userId);
}
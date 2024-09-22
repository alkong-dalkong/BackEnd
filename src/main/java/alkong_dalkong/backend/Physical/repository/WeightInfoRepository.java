package alkong_dalkong.backend.Physical.repository;

import alkong_dalkong.backend.Physical.entity.WeightInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WeightInfoRepository  extends JpaRepository<WeightInfo, Long> {
    Optional<WeightInfo> findTopByPhysicalInfoUserUserIdOrderByCreatedAtDesc(Long userId);
}

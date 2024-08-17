package alkong_dalkong.backend.Family.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import alkong_dalkong.backend.Family.Domain.Family;

public interface FamilyRepository extends JpaRepository<Family, Long>{
    boolean existsByCode(String code);

    Optional<Family> findByCode(String code);
}

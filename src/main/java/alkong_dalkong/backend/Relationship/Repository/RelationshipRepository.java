package alkong_dalkong.backend.Relationship.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import alkong_dalkong.backend.Relationship.Domain.Relationship;

public interface RelationshipRepository extends JpaRepository<Relationship, Long> {
    Optional<List<Relationship>> findAllByFamilyId(Long familyId);
    
}

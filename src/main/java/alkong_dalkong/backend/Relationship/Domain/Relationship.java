package alkong_dalkong.backend.Relationship.Domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;

// @Entity
@Builder
@AllArgsConstructor
public class Relationship {
    // @Id
    // @GeneratedValue(strategy=GenerationType.AUTO)
    // @Column(name = "relation_id")
    // private long id;

    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "user_id")
    // private long userId;

    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "family_id")
    // private long familyId;
}

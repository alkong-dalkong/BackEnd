package alkong_dalkong.backend.Physical.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeightInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long weightId;

    private float weight;

    private LocalDate createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "physical_id")
    private PhysicalInfo physicalInfo;

}
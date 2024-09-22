package alkong_dalkong.backend.Physical.entity;

import alkong_dalkong.backend.User.Domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhysicalInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long physicalId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "physicalInfo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<WeightInfo> weightInfoList = new ArrayList<>();

}
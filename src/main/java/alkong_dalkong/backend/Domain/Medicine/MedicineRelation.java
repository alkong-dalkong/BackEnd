package alkong_dalkong.backend.Domain.Medicine;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;

@Entity
@Getter @Setter
public class MedicineRelation {
    @Id @GeneratedValue
    @Column(name = "medicine_relation_id")
    private Long id;

    // 약을 복용하는 사용자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medicine_user_id")
    private MedicineUser medicineUser;

    // 약 정보
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medicine_id")
    private Medicine medicine;

    // 복용 횟수 (하루에 몇번)
    private int medicineTimes;
    // 복용량 (한번에 복용량)
    private double dosage;

    // 약 복용 시간
    private Time medicineBreakfast;
    private Time medicineLunch;
    private Time medicineDinner;

    // 기타 메모
    private String medicineMemo;
}

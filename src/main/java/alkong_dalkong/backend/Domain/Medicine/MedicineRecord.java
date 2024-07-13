package alkong_dalkong.backend.Domain.Medicine;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter @Setter
public class MedicineRecord {
    @Id @GeneratedValue
    @Column(name = "medicine_record_id")
    private Long id;
    // 복용 정보를 기록하는 날자
    private Date takenDate;

    // 복용하는 사용자와 약 정보
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medicine_relation_id")
    private MedicineRelation medicineDate;

    // 약 복용 여부
    @Enumerated(EnumType.STRING)
    private MedicineTaken breakfast;
    @Enumerated(EnumType.STRING)
    private MedicineTaken lunch;
    @Enumerated(EnumType.STRING)
    private MedicineTaken dinner;
}

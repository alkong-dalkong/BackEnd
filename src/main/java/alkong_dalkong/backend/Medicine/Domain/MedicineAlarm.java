package alkong_dalkong.backend.Medicine.Domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class MedicineAlarm {
    @Id @GeneratedValue
    @Column(name = "medicine_alarm_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medicine_relation_id")
    private MedicineRelation medicineRelation;

    private LocalDateTime alarmDateTime;


    // 생성 메서드
    public void setMedicineAlarm(MedicineRelation medicineRelation, LocalDateTime dateTime){
        this.medicineRelation = medicineRelation;
        this.alarmDateTime = dateTime;
    }
}

package alkong_dalkong.backend.Domain.Medicine;

import alkong_dalkong.backend.Domain.Medicine.Enum.MedicineTaken;
import jakarta.persistence.*;
import lombok.Getter;
import java.time.LocalDate;

@Entity
@Getter
public class MedicineRecord {
    @Id @GeneratedValue
    @Column(name = "medicine_record_id")
    private Long id;

    // 복용 정보를 기록하는 날자
    private LocalDate takenDate;

    // 복용하는 사용자와 약 정보
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medicine_relation_id")
    private MedicineRelation medicineRelation;

    // 약 복용 여부
    @Enumerated(EnumType.STRING)
    private MedicineTaken breakfast;
    @Enumerated(EnumType.STRING)
    private MedicineTaken lunch;
    @Enumerated(EnumType.STRING)
    private MedicineTaken dinner;


    // 생성 메서드
    public static MedicineRecord createMedicineRecord(LocalDate day,
                                                      MedicineRelation initMedicineRelation){
        MedicineRecord medicineRecord = new MedicineRecord();
        medicineRecord.takenDate = day;
        medicineRecord.medicineRelation = initMedicineRelation;

        medicineRecord.breakfast = MedicineTaken.NOT_TAKEN;
        medicineRecord.lunch = MedicineTaken.NOT_TAKEN;
        medicineRecord.dinner = MedicineTaken.NOT_TAKEN;

        return medicineRecord;
    }

    // 약 복용 상황
    public void changeBreakfastTaken(MedicineTaken state){
        this.breakfast = state;
    }
    public void changeLunchTaken(MedicineTaken state){
        this.lunch = state;
    }
    public void changeDinnerTaken(MedicineTaken state){
        this.dinner = state;
    }
}

package alkong_dalkong.backend.Domain.Medicine;

import alkong_dalkong.backend.Domain.Medicine.Enum.MedicineAlarm;
import alkong_dalkong.backend.Domain.Medicine.Enum.MedicineWeek;
import alkong_dalkong.backend.Domain.Medicine.Enum.TakenType;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
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
    private Long dosage;
    // 복용 타입
    private TakenType medicineTakenType;

    // 약 복용 시간
    private LocalTime medicineBreakfast = null;
    private LocalTime medicineLunch = null;
    private LocalTime medicineDinner = null;

    // 기타 메모
    private String medicineMemo;

    // 알람 설정
    private MedicineAlarm medicineAlarm;

    // 요일 복용 상황
    private MedicineWeek monday = MedicineWeek.N;
    private MedicineWeek tuesday = MedicineWeek.N;
    private MedicineWeek wednesday = MedicineWeek.N;
    private MedicineWeek thursday = MedicineWeek.N;
    private MedicineWeek friday = MedicineWeek.N;
    private MedicineWeek saturday = MedicineWeek.N;
    private MedicineWeek sunday = MedicineWeek.N;

    // 생성 메서드
    public static MedicineRelation createMedicineRelation(MedicineUser initUser, Medicine initMedicine,
                                                          int times, Long dos, Integer taken_type,
                                                          List<LocalTime> medicine_time,
                                                          String memo, Integer alarm, List<DayOfWeek> week){
        MedicineRelation medicineRelation = new MedicineRelation();
        medicineRelation.medicineUser = initUser;
        medicineRelation.medicine = initMedicine;

        medicineRelation.medicineTimes = times;
        medicineRelation.dosage = dos;

        for (int i = 0; i < medicine_time.size(); i++) {
            switch (i) {
                case 0 -> medicineRelation.medicineBreakfast = medicine_time.get(0);
                case 1 -> medicineRelation.medicineLunch = medicine_time.get(1);
                case 2 -> medicineRelation.medicineDinner = medicine_time.get(2);
            }
        }

        switch(taken_type){
            case 0 -> medicineRelation.medicineTakenType = TakenType.dose;
            case 1 -> medicineRelation.medicineTakenType = TakenType.tablet;
        }

        medicineRelation.medicineMemo = memo;
        if(alarm == 0){
            medicineRelation.medicineAlarm = MedicineAlarm.DISAGREE;
        }
        else if(alarm == 1){
            medicineRelation.medicineAlarm = MedicineAlarm.AGREE;
        }
        else{
            throw new IllegalStateException("알람 설정이 올바르지 않습니다.");
        }

        for (DayOfWeek i : week) {
            switch (i) {
                case MONDAY -> medicineRelation.monday = MedicineWeek.Y;
                case TUESDAY -> medicineRelation.tuesday = MedicineWeek.Y;
                case WEDNESDAY -> medicineRelation.wednesday = MedicineWeek.Y;
                case THURSDAY -> medicineRelation.thursday = MedicineWeek.Y;
                case FRIDAY -> medicineRelation.friday = MedicineWeek.Y;
                case SATURDAY -> medicineRelation.saturday = MedicineWeek.Y;
                case SUNDAY -> medicineRelation.sunday = MedicineWeek.Y;
                default -> throw new IllegalStateException("요일 정보가 틀렸습니다.");
            }
        }

        return medicineRelation;
    }

    // 약 복용하는 시간 리스트
    public List<LocalTime> takenTime(){
        List<LocalTime> timeList = new ArrayList<>();
        if(this.medicineBreakfast != null){
            timeList.add(this.medicineBreakfast);
        }
        if(this.medicineLunch != null){
            timeList.add(this.medicineLunch);
        }
        if(this.medicineDinner != null){
            timeList.add(this.medicineDinner);
        }
        return timeList;
    }

    // 약 복용하는 요일 리스트
    public List<DayOfWeek> possibleWeek() {
        List<DayOfWeek> weekList = new ArrayList<>();
        MedicineWeek[] days = {this.monday, this.tuesday, this.wednesday,
                this.thursday, this.friday, this.saturday, this.sunday};

        for (int i = 0; i < days.length; i++) {
            if (days[i] == MedicineWeek.Y) {
                weekList.add(DayOfWeek.of(i + 1));
            }
        }

        return weekList;
    }
}

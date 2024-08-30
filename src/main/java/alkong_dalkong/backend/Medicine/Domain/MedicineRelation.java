package alkong_dalkong.backend.Medicine.Domain;

import alkong_dalkong.backend.Medicine.Domain.Enum.TakenType;
import alkong_dalkong.backend.User.Domain.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalDate;
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
    @JoinColumn(name = "user_id")
    private User medicineUser;

    // 약 정보
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medicine_id")
    private Medicine medicine;

    // 복용 횟수 (하루에 몇번)
    private Integer medicineTimes;
    // 복용량 (한번에 복용량)
    private Long dosage;
    // 복용 타입
    @Enumerated(EnumType.STRING)
    private TakenType medicineTakenType;

    // 약 복용 시간
    private LocalTime medicineBreakfast = null;
    private LocalTime medicineLunch = null;
    private LocalTime medicineDinner = null;

    // 마지막 복용 날짜
    private LocalDate takenEndDate;

    // 기타 메모
    private String medicineMemo;

    // 알람 설정
    private int medicineAlarm;

    // 요일 복용 상황
    private int monday = 0;
    private int tuesday = 0;
    private int wednesday = 0;
    private int thursday = 0;
    private int friday = 0;
    private int saturday = 0;
    private int sunday = 0;

    // 생성 메서드
    public static MedicineRelation createMedicineRelation(User initUser, Medicine initMedicine,
                                                          Integer times, Long dos, int taken_type,
                                                          List<LocalTime> medicine_time,
                                                          LocalDate endDate, String memo,
                                                          int alarm, List<DayOfWeek> week){
        MedicineRelation medicineRelation = new MedicineRelation();
        medicineRelation.medicineUser = initUser;
        medicineRelation.medicine = initMedicine;
        medicineRelation.medicineTimes = times;
        medicineRelation.dosage = dos;

        switch(taken_type){
            case 0 -> medicineRelation.medicineTakenType = TakenType.DOSE;
            case 1 -> medicineRelation.medicineTakenType = TakenType.TABLET;
        }

        for (int i = 0; i < medicine_time.size(); i++) {
            switch (i) {
                case 0 -> medicineRelation.medicineBreakfast = medicine_time.get(0);
                case 1 -> medicineRelation.medicineLunch = medicine_time.get(1);
                case 2 -> medicineRelation.medicineDinner = medicine_time.get(2);
            }
        }

        medicineRelation.takenEndDate = endDate;
        medicineRelation.medicineMemo = memo;

        // 알람
        medicineRelation.medicineAlarm = alarm;

        for (DayOfWeek i : week) {
            switch (i) {
                case MONDAY -> medicineRelation.monday = 1;
                case TUESDAY -> medicineRelation.tuesday = 1;
                case WEDNESDAY -> medicineRelation.wednesday = 1;
                case THURSDAY -> medicineRelation.thursday = 1;
                case FRIDAY -> medicineRelation.friday = 1;
                case SATURDAY -> medicineRelation.saturday = 1;
                case SUNDAY -> medicineRelation.sunday = 1;
                default -> throw new IllegalStateException("요일 정보가 틀렸습니다.");
            }
        }
        return medicineRelation;
    }

    // 약 정보 변경
    public void changeMedicineRelation(Integer times, Long dos, int taken_type,
                                              List<LocalTime> medicine_time,
                                              LocalDate endDate, String memo,
                                              int alarm, List<DayOfWeek> week){
        this.medicineTimes = times;
        this.dosage = dos;
        switch(taken_type){
            case 0 -> this.medicineTakenType = TakenType.DOSE;
            case 1 -> this.medicineTakenType = TakenType.TABLET;
        }

        this.medicineBreakfast = null; this.medicineLunch = null; this.medicineDinner = null;
        for (int i = 0; i < medicine_time.size(); i++) {
            switch (i) {
                case 0 -> this.medicineBreakfast = medicine_time.get(0);
                case 1 -> this.medicineLunch = medicine_time.get(1);
                case 2 -> this.medicineDinner = medicine_time.get(2);
            }
        }

        this.takenEndDate = endDate;
        this.medicineMemo = memo;
        this.medicineAlarm = alarm;

        // 요일 정보 초기화
        this.monday = 0; this.tuesday = 0; this.wednesday = 0;
        this.thursday = 0; this.friday = 0; this.saturday = 0; this.sunday = 0;
        for (DayOfWeek i : week) {
            switch (i) {
                case MONDAY -> this.monday = 1;
                case TUESDAY -> this.tuesday = 1;
                case WEDNESDAY -> this.wednesday = 1;
                case THURSDAY -> this.thursday = 1;
                case FRIDAY -> this.friday = 1;
                case SATURDAY -> this.saturday = 1;
                case SUNDAY -> this.sunday = 1;
                default -> throw new IllegalStateException("요일 정보가 틀렸습니다.");
            }
        }
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
        int[] days = {this.monday, this.tuesday, this.wednesday,
                this.thursday, this.friday, this.saturday, this.sunday};

        for (int i = 0; i < days.length; i++) {
            if (days[i] == 1) {
                weekList.add(DayOfWeek.of(i + 1));
            }
        }

        return weekList;
    }
}

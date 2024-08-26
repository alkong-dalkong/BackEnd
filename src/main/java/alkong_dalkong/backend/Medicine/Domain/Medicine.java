package alkong_dalkong.backend.Medicine.Domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Medicine {
    @Id @GeneratedValue
    @Column(name = "medicine_id")
    private Long id;
    // 약 이름
    private String medicineName;
    // 약 설명
    private String medicineMemo;


    // 생성 메서드
    public static Medicine createMedicine(String name) {
        Medicine medicine = new Medicine();
        medicine.medicineName = name;

        return medicine;
    }
}

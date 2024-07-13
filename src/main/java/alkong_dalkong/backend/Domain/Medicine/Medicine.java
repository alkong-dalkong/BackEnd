package alkong_dalkong.backend.Domain.Medicine;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Medicine {
    @Id @GeneratedValue
    @Column(name = "medicine_id")
    private Long id;
    // 약 이름
    private String medicineName;
    // 약 설명
    private String medicineMemo;
}

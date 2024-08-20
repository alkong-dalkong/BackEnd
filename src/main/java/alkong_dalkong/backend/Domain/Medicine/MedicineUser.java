package alkong_dalkong.backend.Domain.Medicine;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class MedicineUser {
    @Id @GeneratedValue
    @Column(name = "medicine_user_id")
    private Long id;

    // USER와 연결
    private Long user_id;
}

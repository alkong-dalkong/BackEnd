package alkong_dalkong.backend.Medical.dto;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class MedicalInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long medical_id;

    private String hospital_name;
    private LocalDateTime hospital_date;

    @ElementCollection
    private List<String> medical_part;

    private String medical_memo;
    private LocalDateTime medical_alarm;
}

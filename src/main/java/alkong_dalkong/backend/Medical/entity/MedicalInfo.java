package alkong_dalkong.backend.Medical.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class MedicalInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long medicalId;

    private String hospitalName;
    private LocalDateTime hospitalDate;

    @ElementCollection
    private List<String> medicalPart;

    private String medicalMemo;
    private LocalDateTime medicalAlarm;
}

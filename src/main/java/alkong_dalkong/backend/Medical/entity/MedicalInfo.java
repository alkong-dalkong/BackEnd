package alkong_dalkong.backend.Medical.entity;

import alkong_dalkong.backend.Medical.common.StringListConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Table(name = "medicalInfo")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicalInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "medical_id")
    private Long medicalId;

    @Column(name = "hospital_name")
    private String hospitalName;

    @Column(name = "hospital_date")
    private LocalDateTime hospitalDate;

    // list로 저장
    @Convert(converter = StringListConverter.class)
    @Column(name = "madical_part")
    private List<String> medicalPart;

    @Column(name = "medical_memo")
    private String medicalMemo;

    @Column(name = "medical_alarm")
    private LocalDateTime medicalAlarm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}

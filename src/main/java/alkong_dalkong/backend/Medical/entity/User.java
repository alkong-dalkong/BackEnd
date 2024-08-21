package alkong_dalkong.backend.Medical.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String name;
    private String id;
    private String password;
    private String phoneNumber;
    private LocalDateTime birth;
    private String gender;
    private String role;
    private boolean agree;
}

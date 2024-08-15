package alkong_dalkong.backend.User.Domain;

import java.time.LocalDate;
import java.util.Collection;
import java.util.ArrayList;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String userId;

    @Column(name = "password")
    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "birth")
    private LocalDate birth;
    
    @Column(name = "gender")
    private String gender;

    @Column(name = "role")
    private String role;

    @Column(name = "agree")
    private boolean agree;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new SimpleGrantedAuthority(role));
        return collection;
    }

    @Override
    public String getUsername() {
        return this.userId;
    }

    @Override
    // 계정 만료 여부
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    // 계정 잠금 상태 여부
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    // 비밀번호 만료 여부
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    // 계정 사용 가능 여부
    public boolean isEnabled() {
        if (isCredentialsNonExpired()
                && isAccountNonLocked()
                && isAccountNonExpired()) {
            return true;
        }
        return false;
    }
}

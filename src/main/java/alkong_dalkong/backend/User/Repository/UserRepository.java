package alkong_dalkong.backend.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import alkong_dalkong.backend.Domain.Users.User;

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByUserId(String userId);

    void deleteByUserId(String userId);
}

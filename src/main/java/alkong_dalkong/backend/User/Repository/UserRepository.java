package alkong_dalkong.backend.User.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import alkong_dalkong.backend.User.Domain.User;

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByUserId(String userId);

    void deleteByUserId(String userId);

    boolean existsByUserId(String userId);
}

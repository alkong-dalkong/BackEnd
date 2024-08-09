package alkong_dalkong.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import alkong_dalkong.backend.Domain.Users.User;

public interface UserRepository extends JpaRepository<User, Long>{
    
}

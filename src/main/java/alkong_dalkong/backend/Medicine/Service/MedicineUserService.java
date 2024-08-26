package alkong_dalkong.backend.Medicine.Service;

import alkong_dalkong.backend.Medicine.Domain.MedicineUser;
import alkong_dalkong.backend.Medicine.Repository.MedicineUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MedicineUserService {
    private final MedicineUserRepository medicineUserRepository;

    public void saveMedicineInfo(MedicineUser medicineUser){
        medicineUserRepository.save(medicineUser);
    }

    // userId로 user를 반환
    public Optional<MedicineUser> findUserById(Long medicineUserId){
        return medicineUserRepository.findById(medicineUserId);
    }
}

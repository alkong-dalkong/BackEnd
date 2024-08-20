package alkong_dalkong.backend.Service.Medicine;

import alkong_dalkong.backend.Domain.Medicine.MedicineUser;
import alkong_dalkong.backend.Repository.Medicine.MedicineUserRepository;
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

package alkong_dalkong.backend.Family.Service;

import java.util.List;

import alkong_dalkong.backend.Family.Domain.Family;
import alkong_dalkong.backend.User.Domain.User;

public interface FamilyService {
    List<User> getMembersByFamilyCode(String code) throws Exception;
    
    List<Family> getFamilyCodeByUserId(String userid) throws Exception;
}

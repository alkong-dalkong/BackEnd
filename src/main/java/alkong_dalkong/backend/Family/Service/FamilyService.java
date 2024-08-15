package alkong_dalkong.backend.Service;

import java.util.List;

import alkong_dalkong.backend.Domain.Users.Family;
import alkong_dalkong.backend.Domain.Users.User;

public interface FamilyService {
    List<User> getMembersByFamilyCode(String code) throws Exception;
    
    List<Family> getFamilyCodeByUserId(String userid) throws Exception;
}

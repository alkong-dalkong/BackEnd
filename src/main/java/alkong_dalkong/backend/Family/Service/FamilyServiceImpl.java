package alkong_dalkong.backend.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import alkong_dalkong.backend.Domain.Users.Family;
import alkong_dalkong.backend.Domain.Users.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class FamilyServiceImpl implements FamilyService{

    @Override
    public List<User> getMembersByFamilyCode(String code) throws Exception{
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getMembersByFamilyCode'");
    }

    @Override
    public List<Family> getFamilyCodeByUserId(String userid) throws Exception{
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getFamilyCodeByUserId'");
    }
    
}

package alkong_dalkong.backend.Family.Service;

import java.util.List;

import alkong_dalkong.backend.Family.Domain.Family;
import alkong_dalkong.backend.Family.Dto.Request.CreateFamilyRequestDto;
import alkong_dalkong.backend.Family.Dto.Response.MemberResponseDto;

public interface FamilyService {
    MemberResponseDto getMembersByFamilyCode(String code) throws IllegalArgumentException;
    
    List<Family> getFamilyCodeByUserId(String userid) throws Exception;

    void createFamily(String userId, CreateFamilyRequestDto dto) throws IllegalArgumentException;
}

package alkong_dalkong.backend.Family.Service;

import java.util.List;

import alkong_dalkong.backend.Family.Domain.Family;
// import alkong_dalkong.backend.Family.Dto.Request.CreateFamilyRequestDto;
import alkong_dalkong.backend.Family.Dto.Request.EnterFamilyRequestDto;
import alkong_dalkong.backend.Family.Dto.Response.CreateFamilyResponseDto;
import alkong_dalkong.backend.Family.Dto.Response.FamilyResponseDto;
import alkong_dalkong.backend.Family.Dto.Response.MemberResponseDto;

public interface FamilyService {
    MemberResponseDto getMembersByFamilyCode(String code) throws IllegalArgumentException;
    
    List<Family> getFamilyCodeByUserId(String userid) throws Exception;

    CreateFamilyResponseDto createFamily(/* CreateFamilyRequestDto dto */) throws IllegalArgumentException;

    String enterFamily(EnterFamilyRequestDto dto) throws IllegalArgumentException;
    
    FamilyResponseDto getFamilies() throws IllegalArgumentException;
}

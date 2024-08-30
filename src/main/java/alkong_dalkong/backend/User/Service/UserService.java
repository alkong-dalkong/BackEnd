package alkong_dalkong.backend.User.Service;


import alkong_dalkong.backend.User.Domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import alkong_dalkong.backend.User.Dto.Request.EditPasswordRequestDto;
import alkong_dalkong.backend.User.Dto.Request.SignupRequestDto;
import alkong_dalkong.backend.User.Dto.Request.UserInfoRequestDto;
import alkong_dalkong.backend.User.Dto.Request.ValidateIdRequestDto;
import alkong_dalkong.backend.User.Dto.Response.TokenDto;
import alkong_dalkong.backend.User.Dto.Response.UserInfoResponseDto;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.validation.Valid;

import java.util.Optional;

public interface UserService extends UserDetailsService{
    void createUser(@Valid SignupRequestDto dto) throws IllegalArgumentException;

    TokenDto reissue(Cookie[] cookies) throws NullPointerException, ExpiredJwtException, 
            IllegalArgumentException;

    void deleteUser(Cookie[] cookies);

    UserInfoResponseDto getUserInfoForEdit() throws UsernameNotFoundException;

    void editUserInfo(@Valid UserInfoRequestDto dto) throws IllegalArgumentException, UsernameNotFoundException;

    void editPassword(EditPasswordRequestDto dto) throws IllegalArgumentException;

    boolean validateId(ValidateIdRequestDto dto);

    Optional<User> findUserById(Long user_id);
}

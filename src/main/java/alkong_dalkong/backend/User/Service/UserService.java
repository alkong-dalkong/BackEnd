package alkong_dalkong.backend.Service;

import java.net.http.HttpRequest;

import alkong_dalkong.backend.Dto.Request.SignupRequestDto;
import alkong_dalkong.backend.Dto.Response.TokenDto;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

public interface UserService {
    void createUser(@Valid SignupRequestDto dto) throws IllegalArgumentException;

    TokenDto reissue(Cookie[] cookies) throws NullPointerException, ExpiredJwtException, 
            IllegalArgumentException;

    void deleteUser(Cookie[] cookies);
}

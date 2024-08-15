package alkong_dalkong.backend.Controller;

import org.springframework.web.bind.annotation.RestController;

import alkong_dalkong.backend.Dto.Request.SignupRequestDto;
import alkong_dalkong.backend.Dto.Response.TokenDto;
import alkong_dalkong.backend.Service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@RestController
@RequiredArgsConstructor
public class UserController implements UserOperations{
    private final UserService userService;

    @Override
    public ResponseEntity<?> signup(SignupRequestDto dto) {
        try {
            userService.createUser(dto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입이 완료되었습니다.");
    }

    @Override
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {
        try {
            TokenDto tokens = userService.reissue(request.getCookies());

            response.setHeader("Authorization", "Bearer " + tokens.getAccessToken());
            response.addCookie(createCookie(tokens.getRefreshToken(), 24 * 60 * 60));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body("토큰이 재발급되었습니다.");
    }

    @Override
    public ResponseEntity<?> exit(HttpServletRequest request, HttpServletResponse response) {
        try {
            userService.deleteUser(request.getCookies());
            response.addCookie(createCookie(null, 0));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body("회원탈퇴가 완료되었습니다.");
    }

    private Cookie createCookie(String value, int expiry) {
        Cookie cookie = new Cookie("refresh", value);
        cookie.setMaxAge(expiry);
        cookie.setHttpOnly(true);

        return cookie;
    }
}

package alkong_dalkong.backend.User.Controller;

import org.springframework.web.bind.annotation.RestController;

import alkong_dalkong.backend.User.Dto.Request.EditPasswordRequestDto;
import alkong_dalkong.backend.User.Dto.Request.SignupRequestDto;
import alkong_dalkong.backend.User.Dto.Request.UserInfoRequestDto;
import alkong_dalkong.backend.User.Dto.Request.ValidateIdRequestDto;
import alkong_dalkong.backend.User.Dto.Response.TokenDto;
import alkong_dalkong.backend.User.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


@RestController
@RequiredArgsConstructor
public class UserController implements UserOperations {
    private final UserService userService;

    private ResponseCookie createCookie(String value, int expiry) {
        return ResponseCookie.from("refresh", value)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(expiry)
                .sameSite("None")
                .build();
    }

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
            response.addHeader(HttpHeaders.SET_COOKIE, createCookie(tokens.getRefreshToken(), 24 * 60 * 60).toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body("토큰이 재발급되었습니다.");
    }

    @Override
    public ResponseEntity<?> exit(HttpServletRequest request, HttpServletResponse response) {
        try {
            userService.deleteUser(request.getCookies());
            response.addHeader(HttpHeaders.SET_COOKIE, createCookie(null, 0).toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body("회원탈퇴가 완료되었습니다.");
    }

    @Override
    public ResponseEntity<?> getUserInfoForEdit() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.getUserInfoForEdit());
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> editUserInfo(UserInfoRequestDto dto) {
        try {
            userService.editUserInfo(dto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body("회원정보가 수정되었습니다.");
    }

    @Override
    public ResponseEntity<?> editPassword(EditPasswordRequestDto dto) {
        try {
            userService.editPassword(dto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body("비밀번호가 변경되었습니다.");
    }

    @Override
    public ResponseEntity<?> validateId(ValidateIdRequestDto dto) {
        if (!userService.validateId(dto)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 존재하는 아이디입니다.");
        }
        return ResponseEntity.status(HttpStatus.OK).body("사용 가능한 아이디입니다.");
    }
}
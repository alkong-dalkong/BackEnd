package alkong_dalkong.backend.User.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import alkong_dalkong.backend.User.Dto.Request.EditPasswordRequestDto;
import alkong_dalkong.backend.User.Dto.Request.SignupRequestDto;
import alkong_dalkong.backend.User.Dto.Request.UserInfoRequestDto;
import alkong_dalkong.backend.User.Dto.Request.ValidateIdRequestDto;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface UserOperations {
    @PostMapping("/user/signup")
    ResponseEntity<?> signup(@RequestBody SignupRequestDto dto);

    @PostMapping("/user/reissue")
    ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response);

    @DeleteMapping("/user/exit")
    ResponseEntity<?> exit(HttpServletRequest request, HttpServletResponse response);

    @GetMapping("/mypage/edit-info")
    ResponseEntity<?> getUserInfoForEdit();

    @PutMapping("/mypage/edit-info")
    ResponseEntity<?> editUserInfo(@RequestBody UserInfoRequestDto dto);

    @PostMapping("/mypage/edit-password")
    ResponseEntity<?> editPassword(@RequestBody EditPasswordRequestDto dto);

    @PostMapping("/user/validate-id")
    ResponseEntity<?> validateId(@RequestBody ValidateIdRequestDto dto);
}

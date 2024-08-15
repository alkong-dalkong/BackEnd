package alkong_dalkong.backend.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import alkong_dalkong.backend.Dto.Request.SignupRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface UserOperations {
    @PostMapping("/user/signup")
    ResponseEntity<?> signup(@RequestBody SignupRequestDto dto);

    @PostMapping("/user/reissue")
    ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response);

    @DeleteMapping("/user/exit")
    ResponseEntity<?> exit(HttpServletRequest request, HttpServletResponse response);
}

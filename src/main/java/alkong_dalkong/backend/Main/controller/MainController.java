package alkong_dalkong.backend.Main.controller;

import alkong_dalkong.backend.Main.dto.response.MainResponseDto;
import alkong_dalkong.backend.Main.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/main")
public class MainController {

    @Autowired
    private MainService mainService;

    @GetMapping("/{user_id}/{local_date}")
    public ResponseEntity<?> getMainInfo(@PathVariable("user_id") Long userId,
                                         @PathVariable("local_date")
                                         @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime localDate) {
        try {
            MainResponseDto response = mainService.getMainInfo(userId, localDate);
            return ResponseEntity.ok().body(Map.of("code", 200, "data", response));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("code", 400, "error", "잘못된 입력값 제공"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("code", 500, "error", "예상치 못한 오류 발생"));
        }
    }
}
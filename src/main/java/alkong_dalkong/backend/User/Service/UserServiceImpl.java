package alkong_dalkong.backend.User.Service;

import java.util.Set;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import alkong_dalkong.backend.User.Config.Util.JwtUtil;
import alkong_dalkong.backend.User.Domain.User;
import alkong_dalkong.backend.User.Dto.Request.SignupRequestDto;
import alkong_dalkong.backend.User.Dto.Response.TokenDto;
import alkong_dalkong.backend.User.Repository.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
// import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final Validator validator;
    private final JwtUtil jwtUtil;

    @Override
    public void createUser(SignupRequestDto dto) throws IllegalArgumentException {
        Set<ConstraintViolation<SignupRequestDto>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            throw new IllegalArgumentException("입력값이 올바르지 않습니다.");
        }

        userRepository.save(User.builder()
                .name(dto.getName())
                .userId(dto.getUserId())
                .password(passwordEncoder.encode(dto.getPassword()))
                .phoneNumber(dto.getPhoneNumber())
                .birth(dto.getBirth())
                .gender(dto.getGender().name())
                .role("ROLE_MEMBER")
                .agree(dto.isAgree())
                .build());
    }

    @Override
    public TokenDto reissue(Cookie[] cookies) throws NullPointerException, ExpiredJwtException, 
            IllegalArgumentException {
        String refresh = validateRefresh(cookies);

        String userId = jwtUtil.getUsername(refresh);
        String role = jwtUtil.getRole(refresh);

        // 새 토큰 발급
        String newAccess = jwtUtil.createJwt("access", userId, role, 600000L);
        String newRefresh = jwtUtil.createJwt("refresh", userId, role, 86400000L);

        return new TokenDto(newAccess, newRefresh);
    }

    @Override
    public void deleteUser(Cookie[] cookies) throws NullPointerException, ExpiredJwtException,
            IllegalArgumentException {
        String refresh = validateRefresh(cookies);
                System.out.println(refresh);
        String userId = jwtUtil.getUsername(refresh);
        userRepository.deleteByUserId(userId);
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("사용자 정보를 찾을 수 없습니다."));
    }

    private String validateRefresh(Cookie[] cookies) throws NullPointerException, ExpiredJwtException,
            IllegalArgumentException {
        String refresh = null;
        if (cookies == null) {
            throw new NullPointerException("쿠키가 존재하지 않습니다.");
        }

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("refresh")) {
                refresh = cookie.getValue();
            }
        }

        if (refresh == null) {
            throw new NullPointerException("토큰이 존재하지 않습니다.");
        }

        try {
            jwtUtil.isExpired(refresh);
        } catch (ExpiredJwtException e) {
            throw new ExpiredJwtException(null, null, "refresh토큰이 만료되었습니다.");
        }

        // 토큰이 refresh인지 확인 (발급시 페이로드에 명시)
        String category = jwtUtil.getCategory(refresh);
        if (!category.equals("refresh")) {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }
        return refresh;
    }
}

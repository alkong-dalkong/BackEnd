package alkong_dalkong.backend.User.Service;

import java.util.Optional;
import java.util.Set;

import org.hibernate.Hibernate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import alkong_dalkong.backend.Common.Util.JwtUtil;
import alkong_dalkong.backend.User.Domain.User;
import alkong_dalkong.backend.User.Dto.Request.EditPasswordRequestDto;
import alkong_dalkong.backend.User.Dto.Request.SignupRequestDto;
import alkong_dalkong.backend.User.Dto.Request.UserInfoRequestDto;
import alkong_dalkong.backend.User.Dto.Request.ValidateIdRequestDto;
import alkong_dalkong.backend.User.Dto.Response.TokenDto;
import alkong_dalkong.backend.User.Dto.Response.UserInfoResponseDto;
import alkong_dalkong.backend.User.Repository.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final Validator validator;
    private final JwtUtil jwtUtil;

    @Override
    public void createUser(SignupRequestDto dto) throws IllegalArgumentException {
        Set<ConstraintViolation<SignupRequestDto>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            System.out.println(violations);
            throw new IllegalArgumentException("입력값이 올바르지 않습니다.");
        }

        userRepository.save(User.builder()
                .name(dto.getName())
                .username(dto.getId())
                .password(passwordEncoder.encode(dto.getPassword()))
                .phoneNumber(dto.getPhoneNumber())
                .birth(dto.getBirth())
                .gender(dto.getGender())
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
        userRepository.deleteByUsername(userId);
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(userId)
                .orElseThrow(() -> new UsernameNotFoundException("사용자 정보를 찾을 수 없습니다."));
        Hibernate.initialize(user.getRelationships());
        return user;
    }

    public UserInfoResponseDto getUserInfoForEdit() throws UsernameNotFoundException {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = (User) loadUserByUsername(userId);
        
        return new UserInfoResponseDto(user.getName(), user.getPhoneNumber(), user.getBirth(), 
                user.getGender());
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

    @Override
    public void editUserInfo(@Valid UserInfoRequestDto dto) throws IllegalArgumentException , UsernameNotFoundException{
        Set<ConstraintViolation<UserInfoRequestDto>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            throw new IllegalArgumentException("입력값이 올바르지 않습니다.");
        }

        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = (User) loadUserByUsername(userId);
        user.updateUserInfo(dto);
    }

    @Override
    public void editPassword(EditPasswordRequestDto dto) throws IllegalArgumentException {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = (User) loadUserByUsername(userId);
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("기존 비밀번호가 일치하지 않습니다.");
        }
        user.updatePassword(passwordEncoder.encode(dto.getNewPassword()));
    }

    @Override
    public boolean validateId(ValidateIdRequestDto dto) {
        return !userRepository.existsByUsername(dto.getId());
    }

    public Optional<User> findUserById(Long user_id){
        return userRepository.findById(user_id);
    }
}

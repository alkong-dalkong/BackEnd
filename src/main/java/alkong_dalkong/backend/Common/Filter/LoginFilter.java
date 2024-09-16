package alkong_dalkong.backend.Common.Filter;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import io.jsonwebtoken.io.IOException;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import alkong_dalkong.backend.Common.Util.JwtUtil;
import alkong_dalkong.backend.Relationship.Domain.Relationship;
import alkong_dalkong.backend.User.Domain.User;
import alkong_dalkong.backend.User.Dto.Response.SuccessLoginresponseDto;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    // 스프링 시큐리티로 사용자 검증 진행
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException {
        setUsernameParameter("id");
        String userId = obtainUsername(request);
        String password = obtainPassword(request);

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userId, password,
                null);

        return authenticationManager.authenticate(authToken);
    }

    // 로그인 성공시 실행
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authentication) throws java.io.IOException {
        String username = authentication.getName();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        String role = iterator.next().getAuthority();

        String accessToken = jwtUtil.createJwt("access", username, role, 10 * 60 * 1000L); // 10분
        String refreshToken = jwtUtil.createJwt("refresh", username, role, 24 * 60 * 60 * 1000L); // 24시간

        response.addHeader("Authorization", "Bearer " + accessToken); // 응답 헤더에 access토큰 설정
        response.addCookie(createCookie("refresh", refreshToken)); // 응답시 쿠키에 refresh토큰 저장

        response.setStatus(HttpStatus.OK.value());

        response.setCharacterEncoding("UTF-8");

        // 로그인 성공 시 로그인 한 사용자의 정보를 반환
        User user = (User) authentication.getPrincipal();

        List<Relationship> relationships = user.getRelationships();
        List<String> familyCodes = new ArrayList<>();
        
        for (Relationship relationship : relationships) {
            familyCodes.add(relationship.getFamily().getCode());
        }
        SuccessLoginresponseDto responseDto = new SuccessLoginresponseDto(user.getUserId(), user.getUsername(),
                user.getName(), "");

        try {
            responseDto.setFamilyCode(familyCodes.get(0));
        } catch (Exception e) {
            responseDto.setFamilyCode("");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        String userJsonResponse = objectMapper.writeValueAsString(responseDto);

        response.setContentType("application/json");
        try (PrintWriter writer = response.getWriter()) {
            writer.print(userJsonResponse);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 로그인 실패시 실행하는 메소드
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws java.io.IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain; charset=UTF-8");
        try (PrintWriter writer = response.getWriter()) {
            writer.print("아이디 또는 비밀번호가 일치하지 않습니다.");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 쿠키 생성
    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24 * 60 * 60);
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }
}

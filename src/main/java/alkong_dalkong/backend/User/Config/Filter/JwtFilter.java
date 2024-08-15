package alkong_dalkong.backend.User.Config.Filter;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import alkong_dalkong.backend.User.Config.Util.JwtUtil;
import alkong_dalkong.backend.User.Domain.User;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    @Override
    
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");

        if (authorization == null) {
            filterChain.doFilter(request, response);
            return;
        }
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response); // 다음 필터 체인(로그인 필터)으로 넘어감
            return;
        }

        // Bearer를 떼고 토큰 값만 추출
        String accessToken = authorization.split(" ")[1];

        // 토큰 만료 여부 확인, 만료시 다음 필터로 넘기지 않음
        try {
            jwtUtil.isExpired(accessToken);
        } catch (ExpiredJwtException e) {

            // response body
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = response.getWriter();
            writer.print("엑세스 토큰이 만료되었습니다.");

            // response status code
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // 토큰 종류 추출
        String category = jwtUtil.getCategory(accessToken);

        if (!category.equals("access")) {
            // response body
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = response.getWriter();
            writer.print("유효하지 않은 토큰입니다.");

            // response status code
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // 토큰에서 사용자 정보 추출
        String userId = jwtUtil.getUsername(accessToken);
        String role = jwtUtil.getRole(accessToken);

        User user = User.builder()
                .userId(userId)
                .password("temp")
                .role(role).build();

        Authentication authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        // 세션에 토큰을 통해 사용자를 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}

package alkong_dalkong.backend.Common.config;

import org.springframework.lang.NonNull;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import alkong_dalkong.backend.Common.Filter.JwtFilter;
import alkong_dalkong.backend.Common.Filter.LoginFilter;
import alkong_dalkong.backend.Common.Filter.LogoutFilter;
import alkong_dalkong.backend.Common.Util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtUtil jwtUtil;

    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        LoginFilter loginFilter = new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil);
        loginFilter.setFilterProcessesUrl("/user/login");
        http.csrf((csrf) -> csrf.disable())
                .formLogin((formLogin) -> formLogin.disable())
                .logout((logout) -> logout.disable())
                .httpBasic((httpBasic) -> httpBasic.disable())
                .authorizeHttpRequests((authorizeRequest) -> authorizeRequest
                        .requestMatchers("/", "/login", "/user/login", "/user/signup", "/user/validate-id",
                                "/user/reissue", "/user/exit", "/swagger*/**", 
                                "/v3/api-docs/**")
                        .permitAll()
                        .anyRequest().authenticated())
                .addFilterAt(
                        loginFilter,
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(new JwtFilter(jwtUtil), LoginFilter.class)
                .addFilterAfter(new LogoutFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors((cors) -> cors.configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(@NonNull HttpServletRequest request) {

                        CorsConfiguration configuration = new CorsConfiguration();

                        configuration.setAllowedOrigins(new ArrayList<>(List.of("https://alkong-dalkong.vercel.app/", 
                                "https://alkongdalkong.duckdns.org/")));
                        // configuration.setAllowedOrigins(Collections.singletonList("https://alkong-dalkong.vercel.app/"));
                        configuration.setAllowedMethods(Collections.singletonList("*"));
                        configuration.setAllowCredentials(true);
                        configuration.setAllowedHeaders(Collections.singletonList("*"));
                        configuration.setMaxAge(3600L);
                        configuration.setExposedHeaders(Collections.singletonList("Authorization"));
                        

                        return configuration;
                    }
                }));
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

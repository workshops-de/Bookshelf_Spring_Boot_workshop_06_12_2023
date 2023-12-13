package de.workshops.bookshelf.config;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

  private final JdbcTemplate jdbcTemplate;

  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http
        .authorizeHttpRequests(
            authorize ->
                authorize
                    .anyRequest().authenticated()
        )
        .httpBasic(withDefaults())
        .formLogin(withDefaults())
        .build();
  }

  @Bean
  public UserDetailsService userDetailsService() {
    return username -> {
      String sql = "SELECT * FROM bookshelf_user WHERE username = ?";

      return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new User(
          rs.getString("username"),
          rs.getString("password"),
          Collections.singletonList(
              new SimpleGrantedAuthority(rs.getString("role"))
          )
      ), username);
    };
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}

package project.checkmovie.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import project.checkmovie.service.AccountService;

@EnableWebSecurity        //spring security 를 적용한다는 Annotation
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter { // 스프링 5.7 이상 부터 지원안함 -> 어떻게 할지 찾기 ㅠㅠ
    private final AccountService accountService;
    /**
     * 규칙 설정
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    // antMatchers : 페이지에 접근할 수 있는 권한을 설정한다.
                    .antMatchers( "/login", "/singUp", "/access_denied", "/resources/**").permitAll() // 로그인 권한은 누구나, resources파일도 모든권한
                    // USER, ADMIN 접근 허용
                    .antMatchers("/userAccess").hasRole("USER")
                    .antMatchers("/userAccess").hasRole("ADMIN")
                    .and()
                .formLogin()
                    .loginPage("/login")
                    //.loginProcessingUrl("/login_proc") //구현한 로그인 페이지
                    .defaultSuccessUrl("/user_access") // 로그인 성공시 제공할 페이지
                    .failureUrl("/access_denied") // 인증에 실패했을 때 보여주는 화면 url, 로그인 form으로 파라미터값 error=true로 보낸다.
                    .and()
                .csrf().disable();		//사이트 간 요청 위조 공격 방지 기능 켜기
    }

    /**
     * 로그인 인증 처리 메소드
     * @param auth
     * @throws Exception
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(accountService).passwordEncoder(new BCryptPasswordEncoder());
    }
}
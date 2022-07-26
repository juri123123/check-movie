package project.checkmovie.service;

import project.checkmovie.domain.Account;
import project.checkmovie.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@RequiredArgsConstructor // 생성자 자동 생성 및 final 변수를 의존관계를 자동으로 설정해 준다.
public class AccountService implements UserDetailsService {
    // 회원가입 시 저장시간을 넣어줄 DateTime형
    SimpleDateFormat format = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:sss");
    Date time = new Date();
    String localTime = format.format(time);

    private final UserMapper userMapper;

    @Transactional // 트랜잭션 보장이 된 메소드로 설정 해준다.
    public void joinUser(Account account){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        account.setUserPw(passwordEncoder.encode(account.getUserPw()));
        account.setUserAuth("USER");
        account.setAppendDate(localTime);
        account.setUpdateDate(localTime);
        userMapper.saveUser(account);
    }

    @Override
    public Account loadUserByUsername(String userId) throws UsernameNotFoundException {
        //여기서 받은 유저 패스워드와 비교하여 로그인 인증
        Account account = userMapper.getUserAccount(userId);
        if (account == null){
            throw new UsernameNotFoundException("User not authorized.");
        }
        return account;
    }
}
package project.checkmovie.mapper;

import org.apache.ibatis.annotations.Mapper;
import project.checkmovie.domain.Account;
import project.checkmovie.service.AccountService;

@Mapper
public interface UserMapper {
    //login
    Account getUserAccount(String userId);

    //회원가입
    void saveUser(Account account);
}

package project.checkmovie.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.checkmovie.domain.Account;
import project.checkmovie.service.AccountService;


@Controller
@RequiredArgsConstructor // 생성자 자동 생성 및 final 변수를 의존관계를 자동으로 설정해 준다
public class UserController {
    private final AccountService accountService;

    /**
     * localhost:8080 시 login 으로 redirect
     * @return
     */
    @GetMapping
    public String root() {
        return "redirect:/fe_main/04-login";
    }

    /**
     * 로그인 폼
     * @return
     */
    @GetMapping("/login")
    public String login(){
        return "fe_main/04-login";
    }

    /**
     * 회원가입 폼
     * @return
     */
    @GetMapping("/signup")
    public String signUpForm() {
        return "05-signup";
    }

    /**
     * 로그인 실패 폼
     * @return
     */
    @GetMapping("/access_denied")
    public String accessDenied() {
        return "access_denied";
    }

    /**
     * 회원가입 진행
     * @param account
     * @return
     */
    @PostMapping("/signup")
    public String signUp(Account account) {
        accountService.joinUser(account);
        return "redirect:/fe_main/04-login";
    }

    /**
     * 유저 페이지
     * @param model
     * @param authentication
     * @return
     */
    @GetMapping("/user_access")
    public String userAccess(Model model, Authentication authentication) {
        //Authentication 객체를 통해 유저 정보를 가져올 수 있다.
        Account account = (Account) authentication.getPrincipal();  //userDetail 객체를 가져옴
        model.addAttribute("info", account.getUserId() +"의 "+ account.getUserName()+ "님");      //유저 아이디
        return "user_access";
    }
}
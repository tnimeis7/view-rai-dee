package th.ac.ku.viewraidee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import th.ac.ku.viewraidee.model.Account;
import th.ac.ku.viewraidee.service.AccountService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/signUp")
public class SignUpController {

    @Autowired
    private AccountService accountService;

    @GetMapping
    public String getSignUpPage(Model model , @AuthenticationPrincipal OAuth2User principal){
        return "sign-up";
    }

    @PostMapping
    public String signupUser(@ModelAttribute Account account, Model model) {
        String signupError = null;
        if (!accountService.isUsernameAvailable(account.getUsername())) {
            signupError = "มีชื่อผู้ใช้นี้อยู่ในระบบแล้ว";
        }
        if (signupError == null) {
            account.setRole("user");
            accountService.createAccount(account);
            model.addAttribute("signupSuccess", true);
        }else {
            model.addAttribute("signupError", signupError);
        }
        return "redirect:/";
    }

}

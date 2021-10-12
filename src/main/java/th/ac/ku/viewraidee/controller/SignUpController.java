package th.ac.ku.viewraidee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import th.ac.ku.viewraidee.model.Account;
import th.ac.ku.viewraidee.service.AccountService;
import th.ac.ku.viewraidee.service.AuthenticationService;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/signUp")
public class SignUpController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping
    public String getSignUpPage(){
        return "sign-up";
    }

    @PostMapping
    public String signupUser(@ModelAttribute Account account, Model model, HttpServletRequest request){
        String signupError = null;
        if (!accountService.isUsernameAvailable(account.getUsername())) {
            signupError = "มีชื่อผู้ใช้นี้อยู่แล้ว กรุณาเปลี่ยนชื่อ";
        }
        else if (!accountService.isEmailAvailable(account.getEmail())) {
            signupError = "มี email นี้อยู่แล้ว กรุณาเปลี่ยน email";
        }
        if (signupError == null) {
            String password = account.getPassword();
            accountService.createAccount(account);
            Account newAccount;
            do{
                newAccount = accountService.getById(account.getUsername());
            }while(newAccount==null);
            authenticationService.preAuthenticate(account.getUsername(), password, request);
        }else {
            model.addAttribute("signupError", signupError);
            return "sign-up";
        }
        return "redirect:/";
    }

}

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

@Controller
@RequestMapping("/signUp")
public class SignUpController {

    @Autowired
    private AccountService accountService;

    @GetMapping
    public String getSignUpPage(){
        return "sign-up";
    }

    @PostMapping
    public String signupUser(@ModelAttribute Account account, Model model) {
        String signupError = null;
        if (!accountService.isUsernameAvailable(account.getUsername())) {
            System.out.println("user");
            signupError = "มีชื่อผู้ใช้นี้อยู่แล้ว กรุณาเปลี่ยนชื่อ";
        }
        else if (!accountService.isEmailAvailable(account.getEmail())) {
            System.out.println("email");
            signupError = "มี email นี้อยู่แล้ว กรุณาเปลี่ยน email";
        }
        if (signupError == null) {
            account.setRole("not null");
            accountService.createAccount(account);
            model.addAttribute("signupSuccess", true);
        }else {
            model.addAttribute("signupError", signupError);
        }
        return "redirect:/";
    }

}

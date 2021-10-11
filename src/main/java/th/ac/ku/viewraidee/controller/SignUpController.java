package th.ac.ku.viewraidee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import th.ac.ku.viewraidee.model.Account;
import th.ac.ku.viewraidee.service.AccountService;
import th.ac.ku.viewraidee.service.AuthenticationService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;

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
            account.setRole("user");
            accountService.createAccount(account);
            Account newAccount = accountService.getById(account.getUsername());
            while(newAccount==null){
                newAccount = accountService.getById(account.getUsername());
            }
            authenticateUserAndSetSession(newAccount, request);

        }else {
            model.addAttribute("signupError", signupError);
        }
        return "redirect:/";
    }

    private void authenticateUserAndSetSession(Account account, HttpServletRequest request) {
        String username = account.getUsername();
        String password = account.getPassword();
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        request.getSession();
        token.setDetails(new WebAuthenticationDetails(request));
        Authentication authenticatedUser = authenticationService.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
    }

}

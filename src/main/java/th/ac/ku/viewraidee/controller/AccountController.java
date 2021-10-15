package th.ac.ku.viewraidee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import th.ac.ku.viewraidee.model.Account;
import th.ac.ku.viewraidee.service.AccountService;
import th.ac.ku.viewraidee.service.AuthenticationService;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/edit")
    public String getEditAccountPage(Model model) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Account account = accountService.getById(username);
        model.addAttribute("account", account);
        return "edit-account";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute Account account, HttpServletRequest request) {
        String passwordField = account.getPassword();
        String currentUsername = authenticationService.getCurrentUsername();
        Account currentAccount = accountService.getById(currentUsername);
        setStaticInfo(account, currentAccount);
        if(!(account.getUsername().equals(currentUsername))){
            String password = account.getPassword();
            accountService.createAccount(account);
            if(passwordField.isEmpty()){
                System.out.println(currentAccount.getPassword());
                account.setPassword(currentAccount.getPassword());
                accountService.update(account);
            }
            Account newAccount;
            do{
                newAccount = accountService.getById(account.getUsername());
            }while(newAccount==null);
            authenticationService.preAuthenticate(account.getUsername(), password, request);
            System.out.println(account.getUsername()+" "+password);
            accountService.delete(currentUsername);
        }
        else{
            if(passwordField.isEmpty()){
                account.setPassword(currentAccount.getPassword());
            }
            else{
                String hashedPassword = passwordEncoder.encode(account.getPassword());
                account.setPassword(hashedPassword);
            }
            accountService.update(account);
        }
        return "redirect:/";
    }

    public void setStaticInfo(Account account, Account currentAccount){
        account.setCountArticle(currentAccount.getCountArticle());
        account.setCountHeart(currentAccount.getCountHeart());
        account.setPhoto(currentAccount.getPhoto());
        account.setRole(currentAccount.getRole());
    }

}

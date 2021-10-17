package th.ac.ku.viewraidee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import th.ac.ku.viewraidee.model.Account;
import th.ac.ku.viewraidee.service.AccountService;
import th.ac.ku.viewraidee.service.AuthenticationService;
import th.ac.ku.viewraidee.service.FileService;

import javax.servlet.http.HttpServletRequest;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private FileService fileService;

    private Logger logger;

    @GetMapping()
    public String getAccountPage(Model model) throws Exception {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Account account = accountService.getById(username);
        model.addAttribute("username", account.getUsername());
        model.addAttribute("photo", account.getPhoto());
        model.addAttribute("link", "Link: "+ account.getLink());
        model.addAttribute("aboutMe", account.getAboutMe());
        model.addAttribute("articleCount", account.getCountArticle());
        model.addAttribute("heartCount", account.getCountHeart());
        return "account";
    }

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
            if(currentAccount.getPassword()!=null&&passwordField.isEmpty()){
                account.setPassword(currentAccount.getPassword());
                accountService.update(account);
            }
            else if(currentAccount.getPassword()==null){
                password = "";
                account.setEmail(currentAccount.getEmail());
                accountService.update(account);
            }
            authenticationService.preAuthenticate(account.getUsername(), password, request);
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
        return "redirect:/account";
    }

    @PostMapping("/delete")
    public String delete(HttpServletRequest request) {
        String currentUsername = authenticationService.getCurrentUsername();
        accountService.delete(currentUsername);
        while(accountService.isUsernameAvailable(currentUsername)){};
        authenticationService.preAuthenticate(currentUsername, "", request);
        return "redirect:/";
    }

    public void setStaticInfo(Account account, Account currentAccount){
        account.setCountArticle(currentAccount.getCountArticle());
        account.setCountHeart(currentAccount.getCountHeart());
        account.setPhoto(currentAccount.getPhoto());
        account.setRole(currentAccount.getRole());
    }

//    public Object upload(@RequestParam("file") MultipartFile multipartFile) {
//        logger.info("HIT -/upload | File Name : {}", multipartFile.getOriginalFilename());
//        return fileService.upload(multipartFile);
//    }


}

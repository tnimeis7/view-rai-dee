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
import th.ac.ku.viewraidee.model.Article;
import th.ac.ku.viewraidee.service.AccountService;
import th.ac.ku.viewraidee.service.ArticleService;
import th.ac.ku.viewraidee.service.AuthenticationService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
    private ArticleService articleService;

    @Autowired
    private ArticleController articleController;

    @GetMapping()
    public String getAccountPage(Model model) throws Exception {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Account account = accountService.getById(username);
        model.addAttribute("username", account.getUsername());
        model.addAttribute("photo", account.getPhoto());
        model.addAttribute("link", "Link: "+ account.getLink());
        model.addAttribute("aboutMe", account.getAboutMe());
        model.addAttribute("articleCount", "จำนวนบทความรีวิว: " + account.getCountArticle());
        model.addAttribute("heartCount", "จำนวนหัวใจที่ได้รับ: " + account.getCountHeart());
        List<Article> onwArticle = articleController.getOwnArticles(username);
        model.addAttribute("ownArticle", onwArticle);
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
    public String edit(@ModelAttribute Account account, HttpServletRequest request) throws InterruptedException {
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

    @PostMapping("/changeImg")
    public String changeImg(@ModelAttribute Account account){
        String newPh = account.getPhoto();
        String currentUsername = authenticationService.getCurrentUsername();
        Account currentAccount = accountService.getById(currentUsername);
        currentAccount.setPhoto(newPh);
        accountService.update(currentAccount);
        currentAccount = accountService.getById(currentUsername);
        while(!(currentAccount.getPhoto().equals(newPh))){
            currentAccount = accountService.getById(currentUsername);
        }
        return "redirect:/account/edit";
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
}

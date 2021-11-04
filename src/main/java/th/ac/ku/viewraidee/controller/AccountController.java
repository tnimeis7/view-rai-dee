package th.ac.ku.viewraidee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
        String password = "";
        String currentUsername = authenticationService.getCurrentUsername();
        Account currentAccount = accountService.getById(currentUsername);
        setStaticInfo(account, currentAccount);
        if(!(account.getUsername().equals(currentUsername))){ //change username
            accountService.createAccount(account);
            if(currentAccount.getPassword()==null){ //social account
                account.setEmail(currentAccount.getEmail()); //เพราะ email โดนให้แก้ไขไม่ได้
            }
            else {
                password = currentAccount.getPassword();
            }
            account.setPassword(password);
            accountService.update(account);
            authenticationService.preAuthenticate(account.getUsername(), "", request);
            accountService.delete(currentUsername);
        }
        else{
            account.setPassword(currentAccount.getPassword());
            accountService.update(account);
        }
        return "redirect:/account";
    }

    @PostMapping("/password/edit")
    public String editPassword(@ModelAttribute Account account, Model model) {
        String changePasswordError = null;
        String currentUsername = authenticationService.getCurrentUsername();
        Account currentAccount = accountService.getById(currentUsername);
        String value[] = splitField(account.getPassword());
        if(accountService.checkMatch(value[0], currentAccount.getPassword())){
            String hashedPassword = passwordEncoder.encode(value[1]);
            currentAccount.setPassword(hashedPassword);
            accountService.update(currentAccount);
        }
        else{
            changePasswordError = "รหัสผ่านปัจจุบันไม่ถูกต้อง";
            model.addAttribute("account", currentAccount);
            model.addAttribute("changePasswordError", changePasswordError);
            return "edit-account";
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

    public String[] splitField(String fieldValue){
        return fieldValue.split(",");
    }
}

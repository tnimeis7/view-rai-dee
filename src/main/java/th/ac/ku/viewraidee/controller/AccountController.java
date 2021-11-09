package th.ac.ku.viewraidee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import th.ac.ku.viewraidee.model.Account;
import th.ac.ku.viewraidee.model.Article;
import th.ac.ku.viewraidee.model.Comment;
import th.ac.ku.viewraidee.service.AccountService;
import th.ac.ku.viewraidee.service.ArticleService;
import th.ac.ku.viewraidee.service.AuthenticationService;
import th.ac.ku.viewraidee.model.*;
import th.ac.ku.viewraidee.service.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.LinkedHashMap;
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

    @Autowired
    private ReportService reportService;

    @Autowired
    private FeedbackService feedbackService;

    @GetMapping()
    public String getAccountPage(Model model) throws Exception {
        Account account = authenticationService.getCurrentAccount();
        model.addAttribute("user",account.getUsername());
        model.addAttribute("role", null);
        model.addAttribute("account",account);
        if(account.getRole().equals("user")){
            List<Article> onwArticle = articleController.getOwnArticles(account.getUsername());
            model.addAttribute("ownArticle", onwArticle);
            return "account";
        }
        else{
            List<Report> reports = reportService.getAllReport();
            List<Report> comment = new ArrayList<>();
            List<Report> article = new ArrayList<>();
            for (Report report : reports) {
                if(report.getType().equals("comment")) {
                    comment.add(report);
                }else {
                    article.add(report);
                }
            }

            LinkedHashMap<Report, Comment> reportComments = new LinkedHashMap<>();
            if(reports!=null) {
                for(Report var: comment) {
                    Comment reportCm = articleService.getCommentById(var.getMentionedId());
                    reportComments.put(var, reportCm);
                }
            }

            LinkedHashMap<Report, Article> reportArticles = new LinkedHashMap<>();
            if(reports!=null) {
                for(Report var: article) {
                    Article reportAtc = articleService.getById(var.getMentionedId());
                    reportArticles.put(var, reportAtc);
                }
            }

            model.addAttribute("commentCount", comment.size());
            model.addAttribute("articleCount", article.size());
            model.addAttribute("articleReport", reportArticles);
            model.addAttribute("commentReport", reportComments);

            List<Feedback> feedbacks = feedbackService.getAllFeedback();
            model.addAttribute("feedbackCount", feedbacks.size());
            model.addAttribute("feedbacks", feedbacks);
            return "admin";
        }
    }

    @GetMapping("{sendingAccount}")
    public String getOtherAccount(Model model, @PathVariable String sendingAccount) throws Exception {
        Account account = authenticationService.getCurrentAccount();
        if(account!=null){
            model.addAttribute("user",account.getUsername());
            if(account.getRole().equals("admin")){
                model.addAttribute("role", account.getRole());
            }
        }
        else{
            model.addAttribute("user","ผู้เยี่ยมชม");
        }
        Account otherAcc = accountService.getById(sendingAccount);
        model.addAttribute("account",otherAcc);
        List<Article> onwArticle = articleController.getOwnArticles(otherAcc.getUsername());
        model.addAttribute("ownArticle", onwArticle);
        return "account";
    }

    @GetMapping("/edit")
    public String getEditAccountPage(Model model) throws Exception {
        Account account = authenticationService.getCurrentAccount();
        model.addAttribute("user",account.getUsername());
        model.addAttribute("account", account);
        return "edit-account";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute Account account, HttpServletRequest request) throws InterruptedException {
        String password = "";
        Account currentAccount = authenticationService.getCurrentAccount();
        setStaticInfo(account, currentAccount);
        if(!(account.getUsername().equals(currentAccount.getUsername()))){ //change username
            accountService.createAccount(account);
            articleService.setUsernameOfComment(currentAccount.getUsername(), account.getUsername());
            articleService.setUsernameOfArticle(currentAccount.getUsername(), account.getUsername());
            if(currentAccount.getPassword()==null){ //social account
                account.setEmail(currentAccount.getEmail()); //เพราะ email โดนให้แก้ไขไม่ได้
                account.setPassword(null);
            }
            else {
                password = currentAccount.getPassword();
                account.setPassword(password);
            }
            accountService.update(account);
            authenticationService.preAuthenticate(account.getUsername(), "", request);
            accountService.delete(currentAccount.getUsername());
        }
        else{
            if(currentAccount.getPassword()==null){ //social account
                account.setEmail(currentAccount.getEmail()); //เพราะ email โดนให้แก้ไขไม่ได้
            }
            account.setPassword(currentAccount.getPassword());
            accountService.update(account);
        }
        TimeUnit.SECONDS.sleep(1);
        return "redirect:/account";
    }

    @PostMapping("/password/edit")
    public String editPassword(@ModelAttribute Account account, Model model) throws InterruptedException {
        String changePasswordError = null;
        Account currentAccount = authenticationService.getCurrentAccount();
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
        TimeUnit.SECONDS.sleep(1);
        return "redirect:/account";
    }

    @PostMapping("/changeImg")
    public String changeImg(@ModelAttribute Account account){
        String newPh = account.getPhoto();
        System.out.println(account.getPhoto());
        Account currentAccount = authenticationService.getCurrentAccount();
        currentAccount.setPhoto(newPh);
        accountService.update(currentAccount);
        currentAccount = accountService.getById(currentAccount.getUsername());
        while(!(currentAccount.getPhoto().equals(newPh))){
            currentAccount = accountService.getById(currentAccount.getUsername());
        }
        return "redirect:/account/edit";
    }

    @PostMapping("/delete")
    public String delete(HttpServletRequest request) {
        String currentUsername = authenticationService.getCurrentUsername();
        accountService.delete(currentUsername);
        articleService.deleteCommentByUsername(currentUsername);
        articleService.deleteArticleByUsername(currentUsername);
        while(accountService.isUsernameAvailable(currentUsername)){};
        authenticationService.preAuthenticate(currentUsername, "", request);
        return "redirect:/";
    }

    @PostMapping("/delete/{username}")
    public String deleteOtherAcc(HttpServletRequest request, @ModelAttribute Account otherAccount) {
        Account deleteAcc = accountService.getById(otherAccount.getUsername());
        accountService.delete(deleteAcc.getUsername());
        articleService.deleteCommentByUsername(otherAccount.getUsername());
        articleService.deleteArticleByUsername(otherAccount.getUsername());
        return "redirect:/";
    }

    @RequestMapping("/plus-heart/{username}")
    public String plusHeartOtherAcc(@PathVariable String username, RedirectAttributes redirectAttrs) {
        accountService.plusHeartUser(username);
        redirectAttrs.addAttribute("id", username);
        return "redirect:/account/{id}";
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

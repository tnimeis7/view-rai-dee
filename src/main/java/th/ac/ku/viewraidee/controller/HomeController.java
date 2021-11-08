package th.ac.ku.viewraidee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import th.ac.ku.viewraidee.model.Account;
import th.ac.ku.viewraidee.service.AccountService;
import th.ac.ku.viewraidee.service.ArticleService;
import th.ac.ku.viewraidee.service.AuthenticationService;
import th.ac.ku.viewraidee.service.GenreService;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private GenreService genreService;

    private String typeM = "Movie";

    @GetMapping
    public String getHomePage(Model model, @AuthenticationPrincipal OAuth2User principal, HttpServletRequest request){
        String username;
        model.addAttribute("popularAccount", accountService.getPopularAccount());
        model.addAttribute("allGenre", genreService.getAllGenreName());
        model.addAttribute("greeting", "to our website");
        model.addAttribute("mostPopular", articleService.getMostPopularArticles());
        model.addAttribute("mostPopularType", articleService.getMostPopularArticlesByType(typeM).stream().limit(3).collect(Collectors.toList()));
        if(typeM.equals("Movie")) model.addAttribute("type", "ภาพยนตร์");
        else if(typeM.equals("TVShow")) model.addAttribute("type", "รายการทีวี");
        else if(typeM.equals("Series")) model.addAttribute("type", "ซีรี่ส์");
        else if(typeM.equals("Animation")) model.addAttribute("type", "อนิเมชัน");
        else if(typeM.equals("AnimeJp")) model.addAttribute("type", "อนิเมะญี่ปุ่น");

        if (principal != null){
            usernameEmailCheck(principal, request);
        }
        if((username = authenticationService.getCurrentUsername())!=null){
            model.addAttribute("user", username);
        }
        else {
            model.addAttribute("user", "ผู้เยี่ยมชม");
        }
        return "home";
    }

    @GetMapping("/pop-by-type/{typeIp}")
        public String changeModelAttr(@PathVariable String typeIp) {
            typeM = typeIp;
            return "redirect:/";
        }


    public void usernameEmailCheck(OAuth2User principal, HttpServletRequest request){
        boolean emailIsMatch = false;
        String username;
        String email = principal.getAttribute("email");
        if (accountService.isEmailAvailable(email)) {
            emailIsMatch = true;
        }
        if(!emailIsMatch){
            Account account = new Account();
            account.setUsername(email);
            account.setEmail(email);
            accountService.createAccountFirstTime(account);

            username = email;
        }
        else{
            Account account = accountService.getByEmail(email);
            username = account.getUsername();
        }
        authenticationService.preAuthenticate(username, "", request);
    }

}

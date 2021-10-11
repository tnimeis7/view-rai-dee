package th.ac.ku.viewraidee.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import th.ac.ku.viewraidee.model.Account;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String getHomePage(Model model, @AuthenticationPrincipal OAuth2User principal, @AuthenticationPrincipal Account account){
        String username;
        model.addAttribute("greeting", "to our website");
        if (principal != null){
            model.addAttribute("user", principal.getAttribute("name"));
        }
        else if((username = getCurrentUsername())!=null){
            model.addAttribute("user", username);
        }
        else {
            model.addAttribute("user", "ผู้เยี่ยมชม");
        }
        return "home";
    }

    public String getCurrentUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        if(login.equals("anonymousUser")){
            return null;
        }
        return login;
    }


}

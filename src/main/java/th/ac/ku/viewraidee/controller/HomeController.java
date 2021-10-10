package th.ac.ku.viewraidee.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String getHomePage(Model model, @AuthenticationPrincipal OAuth2User principal){
        model.addAttribute("greeting", "to our website");
        if (principal != null)
            model.addAttribute("user", principal.getAttribute("name"));
        else
            model.addAttribute("user", "Guest");
        return "home";
    }
}

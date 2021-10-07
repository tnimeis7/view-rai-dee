package th.ac.ku.viewraidee.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SignUpController {
    @RequestMapping("/signUp")
    public String getSignUpPage(Model model , @AuthenticationPrincipal OAuth2User principal){
        return "sign-up";
    }
}

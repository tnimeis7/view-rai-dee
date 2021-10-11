package th.ac.ku.viewraidee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import th.ac.ku.viewraidee.model.Account;
import th.ac.ku.viewraidee.service.AccountService;
import th.ac.ku.viewraidee.service.AuthenticationService;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private AccountService accountService;

    @GetMapping
    public String getLoginPage(Model model , @AuthenticationPrincipal OAuth2User principal){
        return "login";
    }

}

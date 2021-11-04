package th.ac.ku.viewraidee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import th.ac.ku.viewraidee.model.Account;
import th.ac.ku.viewraidee.service.AuthenticationService;

@Controller
public class HeaderController {

    @Autowired
    private AuthenticationService authenticationService;

    @RequestMapping("/header")
    public String getHeader(Model model, @AuthenticationPrincipal OAuth2User principal){
        Account account = null;
        String report = null;
        if (principal != null){
            account = authenticationService.getCurrentAccount();
            if(account.getRole().equals("admin")){
                report = "see report";
                model.addAttribute("report", report);
            }
            else {
                model.addAttribute("user", principal.getAttribute("name"));
            }
        }
        else
            model.addAttribute("user", "Guest");
        return "fragments/header";
    }
}

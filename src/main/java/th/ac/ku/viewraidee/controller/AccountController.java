package th.ac.ku.viewraidee.controller;

import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import th.ac.ku.viewraidee.model.Account;
import th.ac.ku.viewraidee.service.AccountService;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/edit")
    public String getEditAccountPage(Model model) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Account account = accountService.getById(username);
        model.addAttribute("account", account);
        return "edit-account";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute Account account, Model model) {
        String username[] = (account.getUsername()).split(",");
        String link[] = (account.getLink()).split(",");
        String aboutMe[] = (account.getAboutMe()).split(",");
        if(accountService.isUsernameAvailable(username[1])){
            Account currentAccount = accountService.getById(username[0]);
//            deleteusername[0];
//            createusername[1];

        }
        else{
            if(account.getPassword()!=null){
//                accountService.update()
            }
            else{

            }
            System.out.println(account.getUsername()+" " +account.getEmail()+" "+account.getPassword());
        }

        System.out.println(account.getUsername()+" + " +account.getEmail()+" + "+account.getPassword()+" + "+account.getLink()
                + " + "+account.getAboutMe()+ " + "+account.getCountArticle());
        //accountService.update(account);
        return "redirect:/";
    }

}

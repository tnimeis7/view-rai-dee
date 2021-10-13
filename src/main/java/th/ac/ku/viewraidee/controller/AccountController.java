package th.ac.ku.viewraidee.controller;

import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import th.ac.ku.viewraidee.model.Account;
import th.ac.ku.viewraidee.service.AccountService;

@Controller
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping("/edit")
    public String getEditAccountPage(Model model) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Account account = accountService.getById(username);
        model.addAttribute("account", account);
        return "edit-account";
    }

    @PostMapping("/edit")
    public String editAccountPage(Model model, Account account, RedirectAttributes redirectAttributes, @RequestParam("image") MultipartFile multipartFile) throws Exception {
        System.out.println("YESSSS");
        return "edit-account";
    }

}

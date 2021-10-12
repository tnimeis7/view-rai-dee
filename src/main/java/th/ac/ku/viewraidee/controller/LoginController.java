package th.ac.ku.viewraidee.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping
    public String getLoginPage(){
        return "login";
    }

    @PostMapping("/failed")
    public String handleFailedLogin(Model model){
        model.addAttribute("failureMsg", "ชื่อผู้ใช้หรือรหัสผ่านไม่ถูกต้อง");
        return "login";
    }

}

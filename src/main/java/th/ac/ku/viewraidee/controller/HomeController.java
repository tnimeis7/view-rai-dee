package th.ac.ku.viewraidee.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String getHomePage(Model model){
        model.addAttribute("greeting", "to our website");
        return "home";
    }
}

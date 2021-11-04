package th.ac.ku.viewraidee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import th.ac.ku.viewraidee.model.ArticleStream;
import th.ac.ku.viewraidee.service.ArticleStreamService;

import java.util.List;

@Controller
@RequestMapping("/articleStreaming")
public class ArticleStreamController {

    @Autowired
    private ArticleStreamService service;

    //ทำงานไม่ได้ แม้จะรีเทิร์นหน้าเดียวกัน เลยให้ ArticleController เรียกใช้ ArticleStreamService เอง
    @GetMapping
    public String getPlatformsEachAtc(Model model, String atcId){
        model.addAttribute("streamPlatform", service.getAllPlatformByAtcId(atcId));
        return "article-id";
    }

}

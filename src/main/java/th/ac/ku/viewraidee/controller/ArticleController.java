package th.ac.ku.viewraidee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import th.ac.ku.viewraidee.model.Article;
import th.ac.ku.viewraidee.service.ArticleService;

@Controller
@RequestMapping("/Article")
public class ArticleController {
    @Autowired
    private ArticleService service;

    @GetMapping
    public String getArticles(Model model){
        model.addAttribute("Articles", service.getAll());
        return "Articles";
    }

    @GetMapping("/create")
    public String getAddPage(){
        return "Article-Create";
    }

    @PostMapping("/create")
    public String addArticle(@ModelAttribute Article article, Model model){
        service.addArticle(article);
        return "redirect:/Article";
    }
}

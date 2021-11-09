package th.ac.ku.viewraidee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import th.ac.ku.viewraidee.model.Article;
import th.ac.ku.viewraidee.model.Tag;
import th.ac.ku.viewraidee.service.ArticleService;

@Controller
@RequestMapping("/articles")
public class ArticleController {
    @Autowired
    private ArticleService service;

    @GetMapping
    public String getArticles(Model model){
        model.addAttribute("articles", service.getAll());
        System.out.println("public String getArticles");
        return "articles";
    }

    @GetMapping("/create")
    public String getAddPage(){
        System.out.println("public String getAddPage()");
        return "article-create";
    }

    @PostMapping("/create")
    public String addArticle(@ModelAttribute Article article,Model model) throws InterruptedException{
        System.out.println(article.toString());
        article.setAtcId(article.generateUUID());
        article.setPublishDate(null);
        service.addArticle(article);
        System.out.println("public String addArticle");
        return "redirect:/articles";
    }


}

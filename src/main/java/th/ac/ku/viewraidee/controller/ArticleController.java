package th.ac.ku.viewraidee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import th.ac.ku.viewraidee.model.Article;
import th.ac.ku.viewraidee.service.ArticleService;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/articles")
public class ArticleController {
    @Autowired
    private ArticleService service;

    @GetMapping
    public String getArticles(Model model){
        model.addAttribute("articles", service.getAll());
        model.addAttribute("atcNewest",
                service.getAll().stream().sorted(Comparator.comparing(Article::getPublishDate))
                        .collect(Collectors.toList()));
        model.addAttribute("atcOldest",
                service.getAll().stream().sorted(Comparator.comparing(Article::getPublishDate).reversed())
                        .collect(Collectors.toList()));
//                Comparator.comparing(Article::getPublishDate));
        return "articles";
    }

    @GetMapping("/{id}")
    public String getArticle(@PathVariable String id, Model model){
        model.addAttribute("article", service.getById(id));
        return "article-id";
    }
//    @GetMapping("/{atcId}")
//    public String getArticle(@PathVariable String atcId, HttpSession httpSession){
//        httpSession.getmodel.addAttribute("article", service.getById(atcId));
//        return "article-id";
//    }


    @GetMapping("/create")
    public String getAddPage(){
        return "article-create";
    }

    @PostMapping("/create")
    public String addArticle(@ModelAttribute Article article, Model model){
        service.addArticle(article);
        return "redirect:/articles";
    }
}

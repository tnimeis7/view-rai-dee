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
        model.addAttribute("byDate",
                service.getAll().stream().sorted(Comparator.comparing(Article::getPublishDate))
                        .collect(Collectors.toList()));
//                Comparator.comparing(Article::getPublishDate));
        return "articles";
    }

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

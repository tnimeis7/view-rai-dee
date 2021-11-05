package th.ac.ku.viewraidee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import th.ac.ku.viewraidee.model.Article;
import th.ac.ku.viewraidee.model.Genre;
import th.ac.ku.viewraidee.service.ArticleService;
import th.ac.ku.viewraidee.service.ArticleStreamService;
import th.ac.ku.viewraidee.service.GenreService;
import th.ac.ku.viewraidee.service.TagService;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/articles")
public class ArticleController {
    @Autowired
    private ArticleService service;

    @Autowired
    private ArticleStreamService articleStreamService;

    @Autowired
    private TagService tagService;

    @Autowired
    private GenreService genreService;

    @GetMapping
    public String getArticles(Model model){
        model.addAttribute("atcNewest", sortArticles(1));
        model.addAttribute("atcOldest", sortArticles(0));
        return "articles";
    }

    private List<Article> sortArticles(int order){
        List<Article> newest = service.getAll().stream().sorted(Comparator.comparing(Article::getPublishDate))
                .collect(Collectors.toList());
        List<Article> oldest = newest;
        if(order == 1) return newest; // 1 is sorting list from newest
        else{ // 0 is sorting List from oldest
            Collections.reverse(oldest);
            return oldest;
        }
    }


    @GetMapping("/{id}")
    public String getArticle(@PathVariable String id, Model model){
        model.addAttribute("article", service.getById(id));
        model.addAttribute("streamPlatforms", articleStreamService.getAllPlatformByAtcId(id));
        model.addAttribute("tags", tagService.getAllTagByAtcId(id));
        model.addAttribute("genres", genreService.getAllGenreByAtcId(id));
//        model.addAttribute("streaming", articleStreamController.getArticleStreams());
        return "article-id";
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

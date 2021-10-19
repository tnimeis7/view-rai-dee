package th.ac.ku.viewraidee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import th.ac.ku.viewraidee.service.ArticleStreamService;

@Controller
@RequestMapping("/articleStreaming")
public class ArticleStreamController {

    @Autowired
    private ArticleStreamService service;

//    @GetMapping
//    public String getArticleStreams(Model model) {
//        model.addAttribute("atcStreams", service.getAll());
//        return "articleStreaming";
//    }

    @GetMapping
    public String getArticleStream(@PathVariable String id, Model model){
        model.addAttribute("atcStream", service.getById(id));

    }

}

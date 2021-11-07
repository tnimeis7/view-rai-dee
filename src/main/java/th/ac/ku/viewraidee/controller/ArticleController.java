package th.ac.ku.viewraidee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import th.ac.ku.viewraidee.model.*;
import th.ac.ku.viewraidee.service.*;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
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

    @Autowired
    private AuthenticationService authenticationService;
    
    @Autowired
    private AccountService accountService;

    @GetMapping
    public String getArticles(Model model){
        String username = authenticationService.getCurrentUsername();
        if (username != null) {
            model.addAttribute("user", username);
        }else{
            model.addAttribute("user","ผู้เยี่ยมชม");
        }
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
        Account account = authenticationService.getCurrentAccount();
        if (account != null) {
            model.addAttribute("user", account.getUsername());
            model.addAttribute("loginUser", account);
        }else{
            model.addAttribute("user","ผู้เยี่ยมชม");
        }
        model.addAttribute("article", service.getById(id));
        model.addAttribute("streamPlatforms", articleStreamService.getAllPlatformByAtcId(id));
        model.addAttribute("tags", tagService.getAllTagByAtcId(id));
        model.addAttribute("genres", genreService.getAllGenreByAtcId(id));
        List<Comment> comments = service.getCommentByAtcId(id);
        comments = sortCommentByTime(comments);
//        for (Comment var: comments) {
//            System.out.println(var.getCommentDate());
//        }
        LinkedHashMap<Comment, String> articleComments = new LinkedHashMap<>();
        if(comments!=null){
            for (Comment var: comments) {
                Account commentAcc = accountService.getById(var.getUsername());
                articleComments.put(var, commentAcc.getPhoto());
            }
        }
        model.addAttribute("comments", articleComments);
        //report choice
//        List<String> report = new ArrayList<>();
//        report.add("เนื้อหาหยาบคาย");
//        report.add("ลามกอนาจาร");
//        report.add("หมิ่นประมาททำให้ผู้อื่นเสื่อมเสียชื่อเสียง");
//        report.add("ยุยงให้เกิดการทะเลาะวิวาท");
//        report.add("เนื้อหาคุกคามและกลั่นแกล้ง");
//        model.addAttribute("report", report);
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

    @PostMapping("/comment/add")
    public String addComment(@ModelAttribute Comment comment, @ModelAttribute Article article, RedirectAttributes redirectAttrs){
        comment.setId(comment.generateUUID());
        comment.setArticleId(article.getId());
        comment.setCommentDate(null);
        Account account = authenticationService.getCurrentAccount();
        comment.setUsername(account.getUsername());
        service.addComment(comment);
        redirectAttrs.addAttribute("id", article.getId());
        return "redirect:/articles/{id}";
    }

    @RequestMapping("/heart/{id}")
    public String plusHeart(@PathVariable String id, RedirectAttributes redirectAttrs){
        service.plusHeart(id);
        redirectAttrs.addAttribute("id", id);
        return "redirect:/articles/{id}";
    }

    public List<Article> getOwnArticles(String username){
        List<Article> articles = service.getAll();
        List<Article> ownArticles = new ArrayList<>();
        for (Article article : articles) {
            if(article.getAuthorName().equals(username)){
                ownArticles.add(article);
            }
        }
        return ownArticles;
    }

    public List<Comment> sortCommentByTime(List<Comment> comments){
        comments.sort(new Comparator<Comment>(){
            DateFormat dateTime = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            public int compare(Comment l1, Comment l2) {
                try {
                    return dateTime.parse(l1.getCommentDate()).compareTo(dateTime.parse(l2.getCommentDate()));
                } catch (ParseException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        });
        return comments;
    }

    @PostMapping("/report/{id}")
    public String report(@PathVariable String id, RedirectAttributes redirectAttrs, @ModelAttribute Report report){
        report.setId(report.generateUUID());
        System.out.println(report.toString());
        service.createReport(report);
        redirectAttrs.addAttribute("id", id);
        return "redirect:/articles/{id}";
    }


}

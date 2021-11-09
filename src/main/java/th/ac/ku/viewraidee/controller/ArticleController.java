package th.ac.ku.viewraidee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import th.ac.ku.viewraidee.model.*;
import th.ac.ku.viewraidee.service.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
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

    @Autowired
    private StreamingPlatformService streamingPlatformService;

    private List<Article> articlesList;
    private String sort = "All";

    @Autowired
    private ReportService reportService;

    @GetMapping
    public String getArticles(Model model){

        String username = authenticationService.getCurrentUsername();
        if (username != null) {
            model.addAttribute("user", username);
        }else{
            model.addAttribute("user","ผู้เยี่ยมชม");
        }
        if (articlesList==null) articlesList = sortArticles(1);
//        model.addAttribute("atcNewest", sortArticles(1));
//        model.addAttribute("atcOldest", sortArticles(0));
        model.addAttribute("articles", articlesList);
        model.addAttribute("platform", streamingPlatformService.getAll());
        model.addAttribute("genres", genreService.getAllGenreName());
        model.addAttribute("sort", sort);
//        model.addAttribute("articles", service.getAll());
        System.out.println("public String getArticles");
        return "articles";
    }

    @GetMapping("/filter/platform/{pfName}")
    public String filterPlatform(@PathVariable String pfName){
        articlesList = new ArrayList<>();
        List<String> atcId = articleStreamService.getAtcIdByPf(pfName);
        for(String id : atcId){
            articlesList.add(service.getById(id));
        }
        sort = pfName;
        return "redirect:/articles";
    }

    @GetMapping("/filter/genre/{genreName}")
    public String filterGenreName(@PathVariable String genreName) {
            /*ส่ง articlesList ไปให้ service แล้วส่งให้ backend query*/
        articlesList = new ArrayList<>();
        List<String> atcId = genreService.getAtcIdByGenreName(genreName);
        for(String id : atcId){
            articlesList.add(service.getById(id));
        }
        sort = genreName;
        return "redirect:/articles";
    }

    @GetMapping("/filter/type/{type}")
    public String filterType(@PathVariable String type) {
        articlesList = service.getArticlesByType(type);
        sort = type;
        return "redirect:/articles";
    }

    @GetMapping("/filter/date/{dateIp}")
    public String filterDate(@PathVariable String dateIp) {
        /*ส่ง articlesList ไปให้ service แล้วส่งให้ backend query*/
        if(dateIp.equals("Newest")) articlesList = sortArticles(1);
        else if(dateIp.equals("Oldest")) articlesList = sortArticles(0);
        else if(dateIp.equals("Popular")) articlesList= service.getMostPopularArticles();
        sort = dateIp;
        return "redirect:/articles";
    }

    private List<Article> sortArticles(int order){
        List<Article> atcDate = service.getAll();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        // old
        Collections.sort(atcDate, (o1, o2) -> LocalDateTime.parse(o1.getPublishDate(), formatter)
                .compareTo(LocalDateTime.parse(o2.getPublishDate(), formatter)));
        if(order == 1) Collections.reverse(atcDate); // new
        return atcDate;
    }


    @GetMapping("/{id}")
    public String getArticle(@PathVariable String id, Model model){
        Account account = authenticationService.getCurrentAccount();
        if (account != null) {
            model.addAttribute("user", account.getUsername());
            model.addAttribute("loginUser", account);
            if (account.getRole().equals("admin")) {
                model.addAttribute("admin", "admin");
            }
            else{
                model.addAttribute("admin", null);
            }
        }else{
            model.addAttribute("user","ผู้เยี่ยมชม");
            model.addAttribute("admin", null);
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
        System.out.println("public String getAddPage()");
        return "article-create";
    }

    @PostMapping("/create")
    public String addArticle(@ModelAttribute Article article,RedirectAttributes redirectAttributes) throws InterruptedException{
        System.out.println(article.toString());
        article.setId(article.generateUUID());
//        article.setAuthorName(article.getAtcName());
        article.setPublishDate(null);
//        article.setCoverPath(article.getCoverPath());
        article.setAuthorName(authenticationService.getCurrentAccount().getUsername());
        System.out.println("username" + authenticationService.getCurrentAccount().getUsername());
        service.addArticle(article);
        articlesList = service.getAll();
//        System.out.println("public String addArticle");
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
            if(article!=null){
                if(article.getAuthorName().equals(username)) {
                    ownArticles.add(article);
                }
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
        service.createReport(report);
        redirectAttrs.addAttribute("id", id);
        return "redirect:/articles/{id}";
    }

    @RequestMapping ("/delete/article/{id}")
    public String deleteAtc(HttpServletRequest request, @PathVariable String id) {
        service.deleteAtc(id);
        return "redirect:/";
    }

    @RequestMapping("/delete/comment/{id}")
    public String deleteComment(HttpServletRequest request, @PathVariable String id, RedirectAttributes redirectAttrs) {
        Comment comment = service.getCommentById(id);
        redirectAttrs.addAttribute("id", comment.getArticleId());
        service.deleteComment(id);
        return "redirect:/articles/{id}";
    }

    @RequestMapping("/manage-report-comment/{id}")
    public String mangeReportComment(@ModelAttribute Report report, @PathVariable String id){
//        ลบ report ออกจาก firebase
//        if(report.getReportContent()ลบ){
//            เรียก method ลบ comment
//        }
        reportService.deleteReport(id);
        if(report.getReportContent().equals("ลบ")){
            Report report2 = reportService.getReport(id);
            service.deleteComment(report2.getMentionedId());
        }
        return "redirect:/account";
    }

    @RequestMapping("/manage-report-article/{id}")
    public String mangeReportArticle(@ModelAttribute Report report, @PathVariable String id){
        reportService.deleteReport(id);
        if(report.getReportContent().equals("ลบ")){
            Report report2 = reportService.getReport(id);
            service.deleteAtc(report2.getMentionedId());
        }
        return "redirect:/account";
    }

}

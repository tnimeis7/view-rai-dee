package th.ac.ku.viewraidee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import th.ac.ku.viewraidee.model.Article;
import th.ac.ku.viewraidee.model.Comment;
import th.ac.ku.viewraidee.model.Report;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ArticleService {
    @Autowired
    private RestTemplate restTemplate;
//    private String url = "http://localhost:8090/Article";

    public List<Article> getAll() {
        String url = "http://localhost:8090/Article";
        ResponseEntity<Article[]> response = restTemplate.getForEntity(url, Article[].class);
        Article[] articles = response.getBody();
        return Arrays.asList(articles);
    }

    public void addArticle(Article article) {
        String url = "http://localhost:8090/Article";
        restTemplate.postForObject(url, article, Article.class);
    }

    public Article getById(String id){
        String url = "http://localhost:8090/Article/" + id;
        ResponseEntity<Article> response = restTemplate.getForEntity(url, Article.class);
        Article article = response.getBody();
        return article;
    }

    public void addComment(Comment comment) {
        String url = "http://localhost:8090/Comment";
        restTemplate.postForObject(url, comment, Comment.class);
    }

    public List<Comment> getCommentByAtcId(String id){
        String url = "http://localhost:8090/Comment/articleId/{id}";
        ResponseEntity<Comment[]> response = restTemplate.getForEntity(url, Comment[].class, id);
        Comment[] comment = response.getBody();
        return Arrays.asList(comment);
    }

    public Comment getCommentById(String id){
        String url = "http://localhost:8090/Comment/{commentId}";
        ResponseEntity<Comment> response = restTemplate.getForEntity(url, Comment.class, id);
        Comment comment = response.getBody();
        return comment;
    }

    public void plusHeart(String id) {
        String url = "http://localhost:8090/Article/heart/" + id;
        restTemplate.postForObject(url, id, Article.class);
    }

    public void createReport(Report report) {
        String url = "http://localhost:8090/Report";
        restTemplate.postForObject(url, report, Report.class);
    }

    public void deleteAtc(String atcId){
        String url = "http://localhost:8090/Article/" + atcId;
        restTemplate.delete(url);
    }

    public void deleteComment(String commentId){
        String url = "http://localhost:8090/Comment/" + commentId;
        restTemplate.delete(url);
    }

    public List<Comment> getCommentByUsername(String username){
        String url = "http://localhost:8090/Comment/username/" + username;
        ResponseEntity<Comment[]> response = restTemplate.getForEntity(url, Comment[].class);
        Comment[] comment = response.getBody();
        return Arrays.asList(comment);
    }

    public List<Article> getArticleByUsername(String username){
        String url = "http://localhost:8090/Article/author/" + username;
        ResponseEntity<Article[]> response = restTemplate.getForEntity(url, Article[].class);
        Article[] articles = response.getBody();
        return Arrays.asList(articles);
    }

    public void setUsernameOfComment(String username, String newUsername){
        String url = "http://localhost:8090/Comment/setUsername/" + username + "/" + newUsername;
        MultiValueMap<String, String> parametersMap = new LinkedMultiValueMap<>();
        parametersMap.add("username", username);
        parametersMap.add("newUsername", newUsername);
        restTemplate.postForObject(url, parametersMap, Comment.class, newUsername);
    }

    public void setUsernameOfArticle(String username,  String newUsername){
        String url = "http://localhost:8090/Article/setUsername/" + username + "/" + newUsername;
        List<String> list = new ArrayList<>();
        MultiValueMap<String, String> parametersMap = new LinkedMultiValueMap<>();
        parametersMap.add("username", username);
        parametersMap.add("newUsername", newUsername);
        restTemplate.postForObject(url, parametersMap, Article.class, newUsername);
    }

    public void deleteCommentByUsername(String username){
        String url = "http://localhost:8090/Comment/delete/" + username;
        restTemplate.postForObject(url, username, Comment.class);
    }

    public void deleteArticleByUsername(String username){
        String url = "http://localhost:8090/Article/delete/" + username;
        restTemplate.postForObject(url, username, Article.class);
    }

    // ทำไมไม่สีเหลือง!!!!!
//    public void deleteArticle(Article article) {
//        String url = "http://localhost:8090/Article";
//        restTemplate.delete(url, article, Article.class);
//    }

}

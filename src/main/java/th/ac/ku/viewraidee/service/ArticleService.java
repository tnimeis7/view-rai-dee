package th.ac.ku.viewraidee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import th.ac.ku.viewraidee.model.Article;

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

    public List<Article> getMostPopularArticles() {
        String url = "http://localhost:8090/Article/MostPopular";
        ResponseEntity<Article[]> response = restTemplate.getForEntity(url, Article[].class);
        Article[] articles = response.getBody();
        return Arrays.asList(articles);
    }

    // ทำไมไม่สีเหลือง!!!!!
//    public void deleteArticle(Article article) {
//        String url = "http://localhost:8090/Article";
//        restTemplate.delete(url, article, Article.class);
//    }

}

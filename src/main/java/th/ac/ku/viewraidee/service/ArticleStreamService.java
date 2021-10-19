package th.ac.ku.viewraidee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import th.ac.ku.viewraidee.model.ArticleStream;

import java.util.Arrays;
import java.util.List;

@Service
public class ArticleStreamService {

    @Autowired
    private RestTemplate restTemplate;

    public List<ArticleStream> getAll() {
        String url = "http://localhost:8090/ArticleStream";
        ResponseEntity<ArticleStream[]> response = restTemplate.getForEntity(url, ArticleStream[].class);
        ArticleStream[] articleStreams = response.getBody();
        return Arrays.asList(articleStreams);
    }

    public void addArticleStream(ArticleStream articleStream) {
        String url = "http://localhost:8090/ArticleStream";
        restTemplate.postForObject(url, articleStream, ArticleStream.class);
    }

    public ArticleStream getById(String id){
        String url = "http://localhost:8090/ArticleStream" + id;
        ResponseEntity<ArticleStream> response = restTemplate.getForEntity(url, ArticleStream.class);
        ArticleStream articleStream = response.getBody();
        return articleStream;
    }


}

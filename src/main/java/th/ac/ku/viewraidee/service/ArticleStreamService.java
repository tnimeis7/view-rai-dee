package th.ac.ku.viewraidee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import th.ac.ku.viewraidee.model.ArticleStream;
import th.ac.ku.viewraidee.model.StreamingPlatform;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

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

    //วนหา streaming platform ของหนังไอดีนี้
//    public List<ArticleStream> getAllStreamNameByAtcId(String atcId){
//        List<ArticleStream> articleStreamList = getAll();
//        List<ArticleStream> result = new ArrayList<>();
//        for(int i=0;i<articleStreamList.size();i++) {
//            if(articleStreamList.get(i).getAtcId() == atcId) result.add(articleStreamList.get(i));
//        }
//        return result;
//    }

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

    public List<StreamingPlatform> getAllPlatformByAtcId(String atcId){
        String url = "http://localhost:8090/ArticleStream/" + atcId + "/platform";
        ResponseEntity<StreamingPlatform[]> response = restTemplate.getForEntity(url, StreamingPlatform[].class);
        StreamingPlatform[] streamingPlatforms = response.getBody();
        return Arrays.asList(streamingPlatforms);
    }

    public List<String> getAtcIdByPf(String platform) {
        String url = "http://localhost:8090/ArticleStream/" + platform + "/article";
        ResponseEntity<String[]> response = restTemplate.getForEntity(url, String[].class);
        String[] atcId = response.getBody();
        return Arrays.asList(atcId);
    }


}

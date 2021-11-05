package th.ac.ku.viewraidee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import th.ac.ku.viewraidee.model.StreamingPlatform;

import java.util.Arrays;
import java.util.List;

@Service
public class StreamingPlatformService {
    @Autowired
    private RestTemplate restTemplate;

    public List<StreamingPlatform> getAll() {
        String url = "http://localhost:8090/Stream";
        ResponseEntity<StreamingPlatform[]> response = restTemplate.getForEntity(url, StreamingPlatform[].class);
        StreamingPlatform[] streamingPlatforms = response.getBody();
        return Arrays.asList(streamingPlatforms);

    }

    public StreamingPlatform getById(String id){
        String url = "http://localhost:8090/ArticleStream" + id;
        ResponseEntity<StreamingPlatform> response = restTemplate.getForEntity(url, StreamingPlatform.class);
        StreamingPlatform stream = response.getBody();
        return stream;
    }

}

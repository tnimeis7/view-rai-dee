package th.ac.ku.viewraidee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import th.ac.ku.viewraidee.model.Tag;

import java.util.Arrays;
import java.util.List;

@Service
public class TagService {
    @Autowired
    private RestTemplate restTemplate;

    public List<Tag> getAllTagByAtcId(String atcId) {
        String url = "http://localhost:8090/Tag/" + atcId + "/tag";
        ResponseEntity<Tag[]> response = restTemplate.getForEntity(url, Tag[].class);
        Tag[] tags = response.getBody();
        return Arrays.asList(tags);
    }

    public void addTag(Tag tag){
        String url = "http://localhost:8090/Tag";
        restTemplate.postForObject(url, tag, Tag.class);
    }

}

package th.ac.ku.viewraidee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import th.ac.ku.viewraidee.model.Genre;

import java.util.Arrays;
import java.util.List;

@Service
public class GenreService {
    @Autowired
    private RestTemplate restTemplate;

    public List<Genre> getAllGenreByAtcId(String atcId){
        String url = "http://localhost:8090/Genre/" + atcId + "/genre";
        ResponseEntity<Genre[]> response = restTemplate.getForEntity(url, Genre[].class);
        Genre[] genres = response.getBody();
        return Arrays.asList(genres);
    }

}

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

    public List<String> getAllGenreName() {
        String url = "http://localhost:8090/Genre/";
        ResponseEntity<String[]> response = restTemplate.getForEntity(url, String[].class);
        String[] genres = response.getBody();
        return Arrays.asList(genres);
    }

    public List<String> getAtcIdByGenreName(String genreName) {
        String url = "http://localhost:8090/Genre/" + genreName + "/article";
        ResponseEntity<String[]> response = restTemplate.getForEntity(url, String[].class);
        String[] atcId = response.getBody();
        return Arrays.asList(atcId);
    }

    public void addGenreWithId(Genre genre) {
        String url = "http://localhost:8090/Genre";
        restTemplate.postForObject(url, genre, Genre.class);
    }

}

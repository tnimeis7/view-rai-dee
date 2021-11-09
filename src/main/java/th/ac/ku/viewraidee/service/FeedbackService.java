package th.ac.ku.viewraidee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import th.ac.ku.viewraidee.model.Feedback;
import th.ac.ku.viewraidee.model.Report;

import java.util.Arrays;
import java.util.List;

@Service
public class FeedbackService {

    @Autowired
    private RestTemplate restTemplate;

    public void createFeedback(Feedback feedback) {
        String url = "http://localhost:8090/Feedback" ;
        restTemplate.postForObject(url, feedback, Feedback.class);
    }

    public List<Feedback> getAllFeedback(){
        String url = "http://localhost:8090/Feedback";
        ResponseEntity<Feedback[]> response = restTemplate.getForEntity(url, Feedback[].class);
        Feedback[] feedbacks = response.getBody();
        return Arrays.asList(feedbacks);
    }

    public void deleteFeedback(String FbId){
        String url = "http://localhost:8090/Feedback/" + FbId;
        restTemplate.delete(url);
    }

    public Feedback getFeedback(String FbId) {
        String url = "http://localhost:8090/Feedback/" + FbId;
        ResponseEntity<Feedback> response = restTemplate.getForEntity(url, Feedback.class);
        Feedback feedback = response.getBody();
        return feedback;
    }

    public void updateFeedback(Feedback feedback){
        String url = "http://localhost:8090/Feedback/"+ feedback.getId();
        restTemplate.put(url, feedback, Feedback.class);
    }
}

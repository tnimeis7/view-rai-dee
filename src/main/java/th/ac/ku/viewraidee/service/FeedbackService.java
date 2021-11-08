package th.ac.ku.viewraidee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import th.ac.ku.viewraidee.model.Account;
import th.ac.ku.viewraidee.model.Article;
import th.ac.ku.viewraidee.model.Feedback;

@Service
public class FeedbackService {

    @Autowired
    private RestTemplate restTemplate;

    public void createFeedback(Feedback feedback) {
        String url = "http://localhost:8090/Feedback" ;
        restTemplate.postForObject(url, feedback, Feedback.class);
    }

}

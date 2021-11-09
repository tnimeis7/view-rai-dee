package th.ac.ku.viewraidee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import th.ac.ku.viewraidee.model.Account;
import th.ac.ku.viewraidee.model.Feedback;
import th.ac.ku.viewraidee.model.Report;
import th.ac.ku.viewraidee.service.AccountService;
import th.ac.ku.viewraidee.service.AuthenticationService;
import th.ac.ku.viewraidee.service.FeedbackService;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/feedback")
public class FeedbackController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/add")
    public String addFeedback(@ModelAttribute Feedback feedback, HttpServletRequest request) throws InterruptedException {
        Account account = authenticationService.getCurrentAccount();
        feedback.setFbId(feedback.generateUUIDForId());
        feedback.setFbBy(account.getUsername());
        feedback.setFbStatus("new");
        feedbackService.createFeedback(feedback);
        return "redirect:/";
    }

    @PostMapping("/manage-feedback/{id}")
    public String mangeFeedback(@ModelAttribute Feedback feedback, @PathVariable String id){
        if(feedback.getFbStatus().equals("กำลังดำเนินการ")){
            Feedback feedback2 = feedbackService.getFeedback(id);
            feedback2.setFbStatus("กำลังดำเนินการ");
            feedbackService.updateFeedback(feedback2);
        }else if(feedback.getFbStatus().equals("เสร็จแล้ว") || feedback.getFbStatus().equals("ไม่สนใจ")){
            feedbackService.deleteFeedback(id);
        }
        return "redirect:/account";
    }




}

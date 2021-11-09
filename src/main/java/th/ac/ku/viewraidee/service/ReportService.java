package th.ac.ku.viewraidee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import th.ac.ku.viewraidee.model.Report;

import java.util.Arrays;
import java.util.List;

@Service
public class ReportService {
    @Autowired
    private RestTemplate restTemplate;

    public List<Report> getAllReport(){
        String url = "http://localhost:8090/Report";
        ResponseEntity<Report[]> response = restTemplate.getForEntity(url, Report[].class);
        Report[] reports = response.getBody();
        return Arrays.asList(reports);
    }

    public void deleteReport(String reportId){
        String url = "http://localhost:8090/Report/" + reportId;
        restTemplate.delete(url);
    }

    public Report getReport(String reportId) {
        String url = "http://localhost:8090/Report/" + reportId;
        ResponseEntity<Report> response = restTemplate.getForEntity(url, Report.class);
        Report report = response.getBody();
        return report;
    }



}


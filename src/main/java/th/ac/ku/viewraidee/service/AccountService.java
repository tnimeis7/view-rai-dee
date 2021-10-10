package th.ac.ku.viewraidee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import th.ac.ku.viewraidee.model.Account;

import java.util.Arrays;
import java.util.List;

@Service
public class AccountService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Account> getAll() {
        String url = "http://localhost:8090/Account";
        ResponseEntity<Account[]> response =
                restTemplate.getForEntity(url, Account[].class);
        Account[] accounts = response.getBody();
        return Arrays.asList(accounts);
    }

    public void createAccount(Account account) {
        String url = "http://localhost:8090/Account";
        account.setRole("user");
        String hashedPassword = passwordEncoder.encode(account.getPassword());
        account.setPassword(hashedPassword);
        restTemplate.postForObject(url, account, Account.class);
    }

    public boolean isUsernameAvailable(String username) {
        String url = "http://localhost:8090/Account/{username}";
        ResponseEntity<Account> account = restTemplate.getForEntity(url, Account.class, username);
        return account!=null;
    }

}

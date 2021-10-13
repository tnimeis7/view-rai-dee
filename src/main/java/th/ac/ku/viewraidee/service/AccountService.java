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
        if(account.getPassword()!=null){
            String hashedPassword = passwordEncoder.encode(account.getPassword());
            account.setPassword(hashedPassword);
        }
        restTemplate.postForObject(url, account, Account.class);
    }

    public Account getById(String username){
        String url = "http://localhost:8090/Account/id/{username}";
        ResponseEntity<Account> response = restTemplate.getForEntity(url, Account.class, username);
        Account account = response.getBody();
        return account;
    }

    public Account getByEmail(String email){
        String url = "http://localhost:8090/Account/email/{email}";
        ResponseEntity<Account> response = restTemplate.getForEntity(url, Account.class, email);
        Account account = response.getBody();
        return account;
    }

    public boolean isUsernameAvailable(String username) {
        Account account = getById(username);
        return account==null;
    }

    public boolean isEmailAvailable(String email){
        Account account = getByEmail(email);
        return account==null;
    }

    public Account updateAccount(Account account, String username){
        String url = "http://localhost:8090/Account/"+username;
        restTemplate.put(url, account, Account.class);
        return account;
    }


}

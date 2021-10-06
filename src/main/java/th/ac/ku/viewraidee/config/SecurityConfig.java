package th.ac.ku.viewraidee.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                //หน้าเว็บไหนไม่ต้องล้อคอินก็เข้าได้มาเพิ่มตรงนี้ด้วย
                .antMatchers("/", "/css/**", "/js/**","/login","/signUp").permitAll()
                .anyRequest().authenticated()

        .and()
                .oauth2Login()
                .defaultSuccessUrl("/").permitAll()

        .and()
                .logout()
                .logoutSuccessUrl("/").permitAll();

    }
}


package uni.edu.registration.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

/**
 * Created by rasoolzadeh
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                //1. in memory -------------------
                .inMemoryAuthentication()
                .passwordEncoder(new BCryptPasswordEncoder())
                .withUser("ali")
                .password("$2a$10$6UT4cMbfrcYzFc3C8uumqOqY8e2XT9OQoStyW.XosbbxH6jJkLWlq") // user
                .roles("admin");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //2:------------- configure(AuthenticationManagerBuilder auth) ------------------
        //auth.inMemoryAuthentication();
        //1:-------------- configure(HttpSecurity http) -----------------
        //http.authorizeHttpRequests().anyRequest().authenticated();
        http.csrf().disable()
                .httpBasic()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                //.and().authorizeRequests().anyRequest().hasRole("admin");
                .and().authorizeRequests().anyRequest().authenticated();
        //.csrf().disable()
        //.authorizeRequests().anyRequest().permitAll();
        //.authorizeRequests().antMatchers("/std", "/std/**").permitAll()
        //.authorizeRequests().anyRequest().authenticated();
    }

}

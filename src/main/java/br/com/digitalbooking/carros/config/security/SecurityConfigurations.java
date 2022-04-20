package br.com.digitalbooking.carros.config.security;

import br.com.digitalbooking.carros.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authenticationService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/categories").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/categories").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/categories/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/cities").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/cities").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/cities/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/products").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/products").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/products/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/users").hasRole("USER")
                .antMatchers(HttpMethod.DELETE, "/users").hasRole("USER")
                .anyRequest().permitAll()
                .and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilterBefore(new AuthenticationByTokenFilter(tokenService, userRepository), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }
}

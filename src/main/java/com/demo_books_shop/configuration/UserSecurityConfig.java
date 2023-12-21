package com.demo_books_shop.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class UserSecurityConfig {
    private final UserDetailsService userDetails;
    @Autowired
    public UserSecurityConfig(UserDetailsService userDetails) {
        this.userDetails = userDetails;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain appSecurity(HttpSecurity http)throws Exception{
        http.authorizeHttpRequests((auth)->auth
                .requestMatchers("/images/**","/fonts/**","/pictures/**","/js/**","/css/**","/logout",
                        "/welcomePage","/registration/**","/login/**","/book/**","/books/**").permitAll()
                        .anyRequest().authenticated())
                .authenticationProvider(userAuthenticationProvider())
                .formLogin((form)-> {
                        form.loginPage("/login").permitAll()
                        .loginProcessingUrl("/login/processing")
                        .defaultSuccessUrl("/welcomePage",true)
                        .failureUrl("/login?error")
                        .usernameParameter("email").passwordParameter("password");
                })
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .logout(logout->
                        logout.logoutUrl("/logout").logoutSuccessUrl("/login")
                                .invalidateHttpSession(true)
                                .clearAuthentication(true)
                                .deleteCookies("JSESSIONID"));
        return http.build();
    }
    @Bean
    public DaoAuthenticationProvider userAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetails);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
//    @Bean
//   public UserDetailsService userDetailsService(){
//        UserDetails user = User.builder()
//                .username("admin@gmail.com")
//                .password(passwordEncoder().encode("admin"))
//                .roles("ADMIN").build();
//        return new InMemoryUserDetailsManager(user);
//    }
}

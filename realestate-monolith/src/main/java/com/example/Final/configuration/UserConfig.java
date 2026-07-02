package com.example.Final.configuration;

//import com.cloudinary.Cloudinary;
//import com.cloudinary.utils.ObjectUtils;
//import io.github.cdimascio.dotenv.Dotenv;
import com.cloudinary.Cloudinary;
import com.example.Final.service.Image.ImageUploadServiceDecorator;
import com.example.Final.service.UserService;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
//@EnableWebSecurity
public class UserConfig {

    @Bean
    public Cloudinary cloudinary(){
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        String cloudinaryUrl = dotenv.get("CLOUDINARY_URL");
        if (cloudinaryUrl == null) {
            cloudinaryUrl = System.getenv("CLOUDINARY_URL");
        }
        System.out.println("Cloud URL: " + cloudinaryUrl);
        return new Cloudinary(cloudinaryUrl);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserService userService) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity, UserSuccessHandler userSuccessHandler) throws Exception {
        httpSecurity.authorizeHttpRequests(config -> config
                        .requestMatchers("/").hasRole("REALTOR")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/user/register", "/home/login-page",
                                "/user/login", "/user/forgotPassword",
                                "/user/resetPassword", "/home/all-listings", "/home/searchByCity",
                                "/home/searchCity","/home/searchForm",
                                "/home/searchByKey","/home/sortByCity",
                                "/listing/listing-info/**","/listing/post-address").permitAll()
                        .anyRequest().authenticated())

                .formLogin(login -> login
                        .loginPage("/home/home")
                        .loginProcessingUrl("/login")
                        .successHandler(userSuccessHandler)
                        .failureUrl("/user/login?error=true")
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessHandler(userSuccessHandler)
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .permitAll()
                )
                .exceptionHandling(exception -> exception.accessDeniedPage("/user/login"));
        return httpSecurity.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("*/image/**", "*/css/**", "*/js/**");
    }
}

package mk.ukim.finki.studentplatform_backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUsernamePasswordAuthenticationProvider customAuthenticationProvider;

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http, UserDetailsService users) throws Exception {
//        http
//                .csrf()
//                .disable()
//                .authorizeHttpRequests(authorize -> authorize.requestMatchers("/","/h2", "/home", "/login", "/register","/courses","/events").permitAll()
//                         .anyRequest().authenticated())
//                .logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/login"))
//                .rememberMe(rememberMe -> rememberMe.userDetailsService(users));
//
//        return http.build();
//
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, UserDetailsService users) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeHttpRequests(authorize -> authorize.requestMatchers("/","/h2", "/home", "/login", "/register","/courses","/events").
                        permitAll().anyRequest().permitAll())
                .logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/login"))
                .rememberMe(rememberMe -> rememberMe.userDetailsService(users));

        return http.build();

    }


//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(customAuthenticationProvider);
//    }
//
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .cors()
//                .and()
//                .authorizeRequests()
//                .requestMatchers("/register/").permitAll()
//                .requestMatchers("/login/").permitAll()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/login").permitAll()
//                .failureUrl("/login?error=BadCredentials")
//                .defaultSuccessUrl("/courses", true)
//                .and()
//                .logout()
//                .logoutUrl("/logout")
//                .clearAuthentication(true)
//                .invalidateHttpSession(true)
//                .deleteCookies("JSESSIONID")
//                .logoutSuccessUrl("/login");
//
//    }


//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http, UserDetailsService users) throws Exception {
//        http
//                .csrf()
//                .disable()
//                .authorizeRequests().requestMatchers("/register/","/login/").permitAll()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/login"))
//                .rememberMe(rememberMe -> rememberMe.userDetailsService(users));
//
//        return http.build();
//
//    }


//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http, UserDetailsService users) throws Exception {
//        http.csrf().disable()
//                .cors()
//                .and()
//                .authorizeRequests().requestMatchers("/register/").permitAll()
//                .requestMatchers("/login/").permitAll()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/login").permitAll()
//                .failureUrl("/login?error=BadCredentials")
//                .defaultSuccessUrl("/students/myCourses", true)
//                .and()
//                .logout()
//                .logoutUrl("/logout")
//                .clearAuthentication(true)
//                .invalidateHttpSession(true)
//                .deleteCookies("JSESSIONID")
//                .logoutSuccessUrl("/login");
//        return http.build();
//
//
//    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST", "DELETE", "PUT"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
//,"/students/myCourses"
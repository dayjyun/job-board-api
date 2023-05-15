package jobboardapi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.context.WebApplicationContext;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true) // this security configuration applies to entire app configuration
public class SecurityConfiguration {

    private MyUserDetailsService myUserDetailsService;

    @Autowired
    public void setMyUserDetailsService(MyUserDetailsService myUserDetailsService) {
        this.myUserDetailsService = myUserDetailsService;
    }

    /**
     * authJwtRequestFilter creates a new instance of the JWTRequestFilter,
     * so we can validate the jwt token.
     * @return a new JWTRequestFilter instance
     */
    @Bean
    public JWTRequestFilter authJwtRequestFilter(){
        return new JWTRequestFilter();
    }

    /**
     * passwordEncoder creates a new instance of the BCryptPasswordEncoder,
     * so we can salt and hash the user's password.
     * @return a new BCryptPasswordEncoder instance
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * filterChain creates a SecurityFilterChain bean (object) that filters all HTTP requests to the server.
     * This method allows us to keep certain endpoints private and others public.
     * @param http is the incoming HTTP request from a user
     * @return a new SecurityFilterChain object
     * @throws Exception if the filter chain has an error
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{ // accepts a http server request
        http.authorizeRequests().antMatchers(HttpMethod.POST,
                    "/api/users/register",
                    "/api/users/login").permitAll()

            .antMatchers(HttpMethod.GET,
                    "/api/businesses",
                    "/api/businesses/{businessId}",
                    "/api/businesses/{businessId}/jobs",
                    "/api/jobs",
                    "/api/jobs/{jobId}",
                    "/api/users/{userId}"

//                        "/api/users/{userId}",
//                        "/api/myProfile",
//                        "/api/myProfile/jobs",
//                        "/api/jobs/{jobId}/applicants"
            ).permitAll()// these are all public urls
            .anyRequest().authenticated() // other urls need authentication
            .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // when you log into a server, you need to maintain a session. add this session so that our java springboot knows we're logged in
            .and().csrf().disable(); // connects front/back end if they're on different servers
        http.addFilterBefore(authJwtRequestFilter(), UsernamePasswordAuthenticationFilter.class); // added for JWT login
        return http.build();
    }

    /**
     * authenticationManager takes in an AuthenticationConfiguration object,
     * and returns an AuthenticationManager instance.
     * An AuthenticationManager can do one of 3 things in its authenticate() method:
     * 1. Return an Authentication (normally with authenticated=true) if it can verify that the input represents a valid principal
     * 2. Throw an AuthenticationException if it believes that the input represents an invalid principal
     * 3. Return null if it cannot decide
     * @param authConfig tells us how to create the AuthenticationManager
     * @return an AuthenticationManager instance
     * @throws Exception if AuthenticationConfiguration object has an error
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    /**
     * authenticationProvider uses myUserDetailsService and passwordEncoder
     * to return an instance of the DaoAuthenticationProvider.
     * The DaoAuthenticationProvider uses the custom myUserDetailsService service to get the user information from the database.
     * @return an authentication object that will contain the fully populated object including the authorization details
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(myUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * myUserDetails returns the current logged-in user's details as an object.
     * @return the user's details as a MyUserDetails object.
     */
    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public MyUserDetails myUserDetails() {
        return (MyUserDetails) SecurityContextHolder
                .getContext() // returns the object that is stored in a thread-local storage
                .getAuthentication()
                .getPrincipal(); // returns UserDetails object containing details of currently logged-in user

    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/h2-console/**");
    }
}

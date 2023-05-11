package jobboardapi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {


    // logger allows us to save information in server's memory, but not hard drive
    Logger logger = Logger.getLogger(JWTRequestFilter.class.getName());

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private JWTUtils jwtUtils;

    /**
     * parseJwt authenticates the json web token.
     * This method is called upon after the user has already been validated.
     * @param request is the jwt to the server
     * @return the jwt key after "bearer "
     */
    private String parseJwt(HttpServletRequest request) {  // the request is what we're sending to the server
        String headerAuth = request.getHeader("Authorization"); // .getHeader is the key-value pair. for example, "Authorization" : "Bearer"
        if (StringUtils.hasLength("headerAuth") && headerAuth.startsWith("Bearer")) {
            return headerAuth.substring(7);
        }
        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

    }
}

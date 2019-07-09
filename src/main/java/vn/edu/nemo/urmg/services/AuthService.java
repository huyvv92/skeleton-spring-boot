package vn.edu.nemo.urmg.services;

import io.github.jhipster.config.JHipsterProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static vn.edu.nemo.core.Constants.*;

@Service
@Transactional
public class AuthService {
    private static Logger logger = LoggerFactory.getLogger(AuthService.class);

    private JHipsterProperties jHipsterProperties;
    private long tokenValidityInMilliseconds;
    private long tokenValidityInMillisecondsForRememberMe;

    @Autowired
    public AuthService(JHipsterProperties jHipsterProperties) {
        this.jHipsterProperties = jHipsterProperties;
        this.tokenValidityInMilliseconds =
                1000 * jHipsterProperties.getSecurity().getAuthentication().getJwt().getTokenValidityInSeconds();
        this.tokenValidityInMillisecondsForRememberMe =
                1000 * jHipsterProperties.getSecurity().getAuthentication().getJwt().getTokenValidityInSecondsForRememberMe();
    }

    public String token(String email, String password, Boolean rememberMe) {
        logger.info("Generate token for user: {}", email);

        Date validity;
        long now = (new Date()).getTime();
        if (rememberMe) {
            validity = new Date(now + this.tokenValidityInMillisecondsForRememberMe);
        } else {
            validity = new Date(now + this.tokenValidityInMilliseconds);
        }
        String token = Jwts.builder()
                .setSubject(email)
                .setExpiration(validity)
                .claim(JWT_SCOPE,getAuthorities(email))
                .claim(JWT_COMPANY_ID, "1")
                //.claim(JWT_SCOPE,new ArrayList<>())
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
        logger.info("Token generated for user {}, token: {}", email, token);

        return token;
    }

    public List<String> getAuthorities(String email) {
        List<String> authorities = new ArrayList<>();
        authorities.add("ROLE_USER");
        if(email.equals("admin")){
            authorities.add("ROLE_ADMIN");
        }
        return authorities;
    }
}

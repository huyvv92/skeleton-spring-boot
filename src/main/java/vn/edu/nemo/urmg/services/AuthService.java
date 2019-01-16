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
                .setSubject("admin")
                .setExpiration(validity)
                //.claim(JWT_SCOPE,getAuthorities(user))
                .claim(JWT_SCOPE,new ArrayList<>())
                .signWith(SignatureAlgorithm.HS512, jHipsterProperties.getSecurity().getAuthentication().getJwt().getSecret())
                .compact();
        logger.info("Token generated for user {}, token: {}", email, token);

        return token;
    }
}

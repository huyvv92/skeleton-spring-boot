package vn.edu.nemo.urmg.endpoints;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.nemo.core.Constants;
import vn.edu.nemo.urmg.dtos.LoginDTO;
import vn.edu.nemo.urmg.services.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthEndpoint {
    private AuthService authService;
    @Autowired
    public AuthEndpoint(AuthService authService) {
        this.authService = authService;
    }

    @RequestMapping(path = "/token", method = RequestMethod.POST)
    public ResponseEntity<JWTToken> token(@RequestBody LoginDTO loginDTO) {
        String token = this.authService.token(loginDTO.getEmail(),loginDTO.getPassword(),loginDTO.getRememberMe());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(Constants.AUTH_HEADER_STRING,"Bearer " + token);
        JWTToken jwtToken = new JWTToken(token);
        return new ResponseEntity<JWTToken>(jwtToken,httpHeaders, HttpStatus.OK);
    }

    /**
     * Object to return as body in JWT Authentication.
     */
    static class JWTToken {

        private String idToken;

        JWTToken(String idToken) {
            this.idToken = idToken;
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }
}

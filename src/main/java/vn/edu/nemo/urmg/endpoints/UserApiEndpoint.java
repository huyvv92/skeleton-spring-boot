package vn.edu.nemo.urmg.endpoints;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.nemo.urmg.dtos.LoginDTO;

@RestController
@RequestMapping("/api/users")
public class UserApiEndpoint {

    @RequestMapping(path = "/current", method = RequestMethod.POST)
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @PreAuthorize ("#model.email == authentication.name")
    @PreAuthorize ("#model.email == principal.username")
    public String currentUser(@RequestBody LoginDTO model) {
        return "successful";
    }
}

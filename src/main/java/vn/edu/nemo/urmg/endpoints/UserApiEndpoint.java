package vn.edu.nemo.urmg.endpoints;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.nemo.core.CrudApiEndpoint;
import vn.edu.nemo.urmg.dtos.LoginDTO;
import vn.edu.nemo.urmg.models.User;
import vn.edu.nemo.urmg.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserApiEndpoint extends CrudApiEndpoint<User, Long> {
    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    //test phân quyền
    @RequestMapping(path = "/current", method = RequestMethod.POST)
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @PreAuthorize ("#model.email == authentication.name")
    @PreAuthorize ("#model.email == principal.username")
    public String currentUser(@RequestBody LoginDTO model) {
        return "successful";
    }

    private UserService userService;

    @Autowired
    public UserApiEndpoint(UserService service){
        super(service);
        this.userService = service;
        this.baseUrl = "/api/users";
    }
}

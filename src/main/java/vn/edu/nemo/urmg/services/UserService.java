package vn.edu.nemo.urmg.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;
import vn.edu.nemo.core.CrudService;
import vn.edu.nemo.urmg.dtos.LoginDTO;
import vn.edu.nemo.urmg.models.User;
import vn.edu.nemo.urmg.repositories.UserRepository;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService extends CrudService<User, Long> {
    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    private UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.repository = this.userRepository = userRepository;
    }

    @PostAuthorize("returnObject.companyId == principal.companyId")
    public LoginDTO getLoginDTO(){
        LoginDTO tmp = new LoginDTO();
        tmp.setCompanyId("2");
        return tmp;
    }
}

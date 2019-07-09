package vn.edu.nemo.postgre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.nemo.core.CrudApiEndpoint;
import vn.edu.nemo.urmg.models.User;

@RestController
@RequestMapping("/api/names")
public class NameApiEndpoint extends CrudApiEndpoint<Name, Long> {
    private NameService nameService;

    @Autowired
    public NameApiEndpoint(NameService service){
        super(service);
        this.nameService = service;
        this.baseUrl = "/api/names";
    }
}

package vn.edu.nemo.postgre;

import org.springframework.stereotype.Service;
import vn.edu.nemo.core.CrudService;

import javax.transaction.Transactional;

@Service
@Transactional
public class NameService extends CrudService<Name, Long> {
    private NameRepository nameRepository;

    public NameService(NameRepository nameRepository){
        this.repository = this.nameRepository = nameRepository;
    }


}

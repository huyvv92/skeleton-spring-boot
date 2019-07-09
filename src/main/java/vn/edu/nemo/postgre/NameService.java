package vn.edu.nemo.postgre;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import vn.edu.nemo.ErpCache;
import vn.edu.nemo.core.CrudService;

import javax.transaction.Transactional;

@Service
@Transactional
public class NameService extends CrudService<Name, Long> {
    private NameRepository nameRepository;

    public NameService(NameRepository nameRepository){
        this.repository = this.nameRepository = nameRepository;
    }

    @Override
    @Cacheable(cacheNames = ErpCache.TRANSFERS,unless = "#result==null")
    public Name get(Long aLong) {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return super.get(aLong);
    }

    @Override
    protected void afterUpdate(Name old, Name updated) {
        super.afterUpdate(old, updated);

    }
}

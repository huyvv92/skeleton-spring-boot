package vn.edu.nemo.core;

import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.RSQLParserException;
import cz.jirutka.rsql.parser.ast.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import vn.edu.nemo.core.errors.BadRequestAlertException;
import vn.edu.nemo.core.errors.UnauthorizedExpcetion;
import vn.edu.nemo.core.rsql.CustomRsqlVisitor;
import vn.edu.nemo.core.utils.SecurityUtils;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

@Transactional
public class CrudService<T extends AbstractEntity, ID extends Serializable> {
    private static Logger logger = LoggerFactory.getLogger(CrudService.class);
    protected CustomJpaRepository<T,ID> repository;

    public T get(ID id) {
        return repository.findOne(id);
    }

    public List<T> findAll() {
        return repository.findAll();
    }

    public Page<T> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public List<T> search(String query) {
        if(StringUtils.isEmpty(query)){
            return repository.findAll();
        }
        Node rootNode = new RSQLParser().parse(query);
        Specification<T> spec = rootNode.accept(new CustomRsqlVisitor<T>());
        return repository.findAll(spec);
    }

    public Page<T> search(String query, Pageable pageable) {
        if(StringUtils.isEmpty(query)){
            return repository.findAll(pageable);
        }
        try {
            Node rootNode = new RSQLParser().parse(query);
            Specification<T> spec = rootNode.accept(new CustomRsqlVisitor<T>());
            return repository.findAll(spec, pageable);
        } catch(RSQLParserException pe) {
            logger.error("SEARCH FAIL: {}",query);
            return emptyPage();
        } catch (Exception e) {
            logger.error("SEARCH FAIL: {}",query);
            return emptyPage();
        }
    }

    public T create(T entity) {
        beforeCreate(entity);
        repository.save(entity);
        afterCreate(entity);
        return entity;
    }

    public T update(ID id, T entity) {
        T old = get(id);
        if(old == null) {
            throw new BadRequestAlertException("Entity not exist any more","common","entityNotExistAnyMore");
        }
        beforeUpdate(entity);

        repository.save(entity);
        afterUpdate(old,entity);
        return entity;
    }

    public void delete(T entity) {
        beforeDelete(entity);
        repository.delete(entity);
        afterDelete(entity);
    }

    public void deleteById(ID id) {
        T entity = get(id);
        delete(entity);
    }

    public Long count() {
        return repository.count();
    }

    public void batchDelete(List<ID> ids) {
        for(ID id : ids) {
            deleteById(id);
        }
    }

    protected void beforeCreate(T entity) {
        entity.setCreated(System.currentTimeMillis());
        entity.setUpdated(System.currentTimeMillis());
        if(entity.getCreatedBy() == null) {
            String currentUsername = SecurityUtils.getCurrentUserLogin();
            if(currentUsername !=null){
                entity.setCreatedBy(currentUsername);
            } else {
                entity.setCreatedBy("System");
            }

        }
        if(entity.getActive() == null) {
            entity.setActive(true);
        }
    }

    protected void afterCreate(T entity) {

    }

    protected void beforeUpdate(T entity) {
        entity.setUpdated(System.currentTimeMillis());
        entity.setUpdatedBy(SecurityUtils.getCurrentUserLogin());
        if(entity.getUpdated() ==null){
            entity.setUpdatedBy("System");
        }
        if(entity.getActive() == null) {
            entity.setActive(true);
        }
    }

    protected void afterUpdate(T old, T updated) {

    }

    protected void beforeDelete(T entity) {
        if("system".equals(entity.getCreatedBy())) {
            //can not delete a system object
            throw new UnauthorizedExpcetion("canNotDeleteSystemObject");
        }
    }

    protected void afterDelete(T entity) {

    }

    public Page<T> emptyPage() {
        return new Page<T>() {
            @Override
            public int getTotalPages() {
                return 0;
            }

            @Override
            public long getTotalElements() {
                return 0;
            }

            @Override
            public <S> Page<S> map(Converter<? super T, ? extends S> converter) {
                return null;
            }

            @Override
            public int getNumber() {
                return 0;
            }

            @Override
            public int getSize() {
                return 0;
            }

            @Override
            public int getNumberOfElements() {
                return 0;
            }

            @Override
            public List<T> getContent() {
                return null;
            }

            @Override
            public boolean hasContent() {
                return false;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public boolean isFirst() {
                return false;
            }

            @Override
            public boolean isLast() {
                return false;
            }

            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }

            @Override
            public Pageable nextPageable() {
                return null;
            }

            @Override
            public Pageable previousPageable() {
                return null;
            }

            @Override
            public Iterator<T> iterator() {
                return null;
            }
        };
    }

    public void deactive(ID id) {
            try {
                T t = get(id);
                t.setActive(false);
                update(id, t);
            } catch (Exception e) {
                //logger.debug("Cannot update deactive object with id #{}, baseUrl: {}",id,this.baseUrl);
            }
    }
}

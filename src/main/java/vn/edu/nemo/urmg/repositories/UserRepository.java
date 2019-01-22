package vn.edu.nemo.urmg.repositories;

import org.springframework.stereotype.Repository;
import vn.edu.nemo.core.CustomJpaRepository;
import vn.edu.nemo.urmg.models.User;

@Repository
public interface UserRepository extends CustomJpaRepository<User, Long> {
}

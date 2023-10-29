package app.web.zyncky.repo;

import java.util.Optional;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import app.web.zyncky.model.User;

@Repository
public interface UserRepository extends ListCrudRepository<User, Integer> {

    public Optional<User> findByUid(String uid);

    public Optional<User> findByUserName(String userName);
    
}

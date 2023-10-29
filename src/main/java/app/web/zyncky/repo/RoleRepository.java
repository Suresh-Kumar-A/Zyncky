package app.web.zyncky.repo;

import java.util.Optional;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import app.web.zyncky.model.Role;

@Repository
public interface RoleRepository extends ListCrudRepository<Role, Integer> {

    public Optional<Role> findByRoleName(String roleName);

}

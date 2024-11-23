package app.web.zyncky.service;

import app.web.zyncky.constant.RoleEnum;
import app.web.zyncky.entity.Role;
import app.web.zyncky.repository.RoleRepository;
import jakarta.annotation.Nonnull;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Data
public final class RoleService {

    final RoleRepository roleRepository;

    public Role createRole(@Nonnull String name) {
        return roleRepository.save(Role.builder().name(RoleEnum.mapToRole(name).name()).build());
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Optional<Role> findRoleByName(String roleName) {
        if (null == roleName || roleName.isEmpty()) {
            return Optional.empty();
        } else {
            return roleRepository.findByName(roleName);
        }
    }
}

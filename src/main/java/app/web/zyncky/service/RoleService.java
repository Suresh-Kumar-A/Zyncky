package app.web.zyncky.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import app.web.zyncky.model.Role;
import app.web.zyncky.repo.RoleRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepo;

    public Role createRole(String roleName) throws IllegalArgumentException {
        if (isRoleNameInvalid(roleName)) throw new IllegalArgumentException("Role Name is Invalid");

        Role newRole = Role.builder().roleName(roleName).build();
        return roleRepo.save(newRole);
    }

    private boolean isRoleNameInvalid(String roleName) {
        return Objects.isNull(roleName) || roleName.trim().isEmpty();
    }

    public Role findRoleByName(String roleName) throws IllegalArgumentException {
        if (isRoleNameInvalid(roleName)) throw new IllegalArgumentException("Role Name is Invalid");

        return roleRepo.findByRoleName(roleName).orElseThrow(() -> new RuntimeException("Role does not exist"));
    }

    public List<Role> getAllRole() {
        return roleRepo.findAll();
    }
}

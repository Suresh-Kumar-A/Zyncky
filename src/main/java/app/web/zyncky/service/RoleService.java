package app.web.zyncky.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import app.web.zyncky.model.Role;
import app.web.zyncky.repo.RoleRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RoleService {

    private RoleRepository roleRepo;

    public Role createRole(String roleName) throws Exception {
        if (!validateRoleName(roleName))
            throw new IllegalArgumentException("Role Name is Invalid");

        Role newRole = Role.builder().roleName(roleName).build();
        return roleRepo.save(newRole);
    }

    private boolean validateRoleName(String roleName) {
        if (Objects.isNull(roleName) || roleName.trim().length() == 0)
            return false;
        else
            return true;
    }

    public Role findRoleByName(String roleName) throws Exception {
        if (!validateRoleName(roleName))
            throw new IllegalArgumentException("Role Name is Invalid");

        return roleRepo.findByRoleName(roleName)
                .orElseThrow(() -> new RuntimeException("Role does not exist"));
    }

    public List<Role> getAllRole() {
        return roleRepo.findAll();
    }
}

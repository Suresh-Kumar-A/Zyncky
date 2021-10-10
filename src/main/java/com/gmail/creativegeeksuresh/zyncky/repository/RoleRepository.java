package com.gmail.creativegeeksuresh.zyncky.repository;

import com.gmail.creativegeeksuresh.zyncky.model.Role;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role,Integer> {

    public Role findByRoleName(String roleName);
    
}

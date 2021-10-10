package com.gmail.creativegeeksuresh.zyncky.service;

import java.util.ArrayList;
import java.util.List;

import com.gmail.creativegeeksuresh.zyncky.model.Role;
import com.gmail.creativegeeksuresh.zyncky.repository.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
  @Autowired
  private RoleRepository roleRepo;

  public Role createRole(Role role) throws Exception {
    return roleRepo.save(role);
  }

  public Role findByRoleName(String role) throws Exception {
    return roleRepo.findByRoleName(role);
  }

  public List<Role> getAllRoles() throws Exception {
    return (ArrayList<Role>) roleRepo.findAll();
  }
}

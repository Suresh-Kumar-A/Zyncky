package com.gmail.creativegeeksuresh.zyncky.repository;

import com.gmail.creativegeeksuresh.zyncky.model.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User,Integer> {

    public User findByUid(String uid);

    public User findByUsername(String username);
    
}

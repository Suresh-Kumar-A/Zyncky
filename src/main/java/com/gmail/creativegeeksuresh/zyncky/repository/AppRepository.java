package com.gmail.creativegeeksuresh.zyncky.repository;

import com.gmail.creativegeeksuresh.zyncky.model.Application;
import com.gmail.creativegeeksuresh.zyncky.model.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppRepository extends CrudRepository<Application,Integer> {

    public User findByAppId(String appId);
    
}

package com.auth.sample.dao;

import com.auth.sample.model.UserModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends CrudRepository<UserModel, Integer> {
    UserModel findByUsername(String username);
}

package com.auth.sample.dao;

import com.auth.sample.model.UserModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends CrudRepository<UserModel, Integer> {
    /**
     * Query DB to find user details by using username
     * @param username username in string format
     * @return UserModel object containing user details
     */
    UserModel findByUsername(String username);
}

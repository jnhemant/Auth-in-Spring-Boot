package com.auth.sample.service;

import com.auth.sample.dao.UserDao;
import com.auth.sample.model.UserAuth;
import com.auth.sample.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    /**
     * Fetch user details from DB using username
     * @param username username of user
     * @return UserDetails fetched from DB
     * @throws UsernameNotFoundException exception may occur in case username if not found in DB
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel userModel = userDao.findByUsername(username);
        if (userModel == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(userModel.getUsername(), userModel.getPassword(),
                new ArrayList<>());
    }

    /**
     * Save current user in DB
     * @param user details of user like username, password
     * @return created UserModel object
     */
    public UserModel save(UserAuth user) {
        UserModel userModel = new UserModel();
        userModel.setUsername(user.getUsername());
        userModel.setPassword(bcryptEncoder.encode(user.getPassword()));
        return userDao.save(userModel);
    }

}

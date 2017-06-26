package io.egen.api.service.impl;


import io.egen.api.entity.User;
import io.egen.api.exception.BadRequestException;
import io.egen.api.exception.PageNotFoundException;
import io.egen.api.respository.UserRepository;
import io.egen.api.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/*
* Service layer in REST API is responsible for implementation of Business Logic.
* It is responsible to make some sort of validations
* Service layer SHOULD NOT directly connect to the database.It is the responsibility of the 'REPOSITORY' layer.
* */

@Service
public class UserServiceImpl implements UserService{
	private UserRepository repository;

    public UserServiceImpl(UserRepository repository){
        this.repository=repository;
    }

    @Override
    @Transactional(readOnly=true)
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly=true)
    public User findOne(String userId) {
        User existingUser=repository.findOne(userId);
        if(existingUser == null){
            throw new PageNotFoundException("User with user id "+userId+ "does not exists");
        }
        return existingUser;
    }

    @Override
    @Transactional
    public User create(User user) {
        User existingUser=repository.findByEmail(user.getEmail());
        if(existingUser != null){
            throw new BadRequestException("User with emai id "+user.getEmail()+ " already exists in the system");
        }
        return repository.create(user);
    }

    @Override
    @Transactional
    public User update(String userId, User user) {
        User existingUser=repository.findOne(userId);
        if(existingUser == null){
            throw new PageNotFoundException("User with user id "+userId+ "does not exists");
        }
        return repository.update(existingUser);
    }

    @Override
    @Transactional
    public void delete(String userId) {
        User existingUser=repository.findOne(userId);
        if(existingUser == null){
            throw new PageNotFoundException("User with user id "+userId+ "does not exists");
        }
        repository.delete(existingUser);
    }
}

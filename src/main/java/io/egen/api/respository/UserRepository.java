package io.egen.api.respository;

import java.util.List;

import io.egen.api.entity.User;

public interface UserRepository {

    public List<User> findAll();
    public User findOne(String userId);
    public User create(User user);
    public User update(User user);
    /*public void delete(String userId);*/
    public void delete(User user);

    public User findByEmail(String email);
}

package edu.miu.cs.cs425.fairfieldlibrarywebapp.service;

import java.util.List;

import edu.miu.cs.cs425.fairfieldlibrarywebapp.model.User;

public interface UserService {
    public List<User> getAllUsers();

    public User saveUser(User user);

    public User getUserById(Integer userId);
}

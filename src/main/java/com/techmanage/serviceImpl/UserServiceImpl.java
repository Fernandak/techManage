package com.techmanage.serviceImpl;


import com.techmanage.entity.User;
import com.techmanage.exception.UserNotFoundException;
import com.techmanage.repository.UserRepository;
import com.techmanage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl  implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuário com ID " + id + " não encontrado."));
    }
    @Override
    public User updateUser(Long id, User user) {
        User existingUser = getUserById(id);
        existingUser.setFullName(user.getFullName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPhone(user.getPhone());
        existingUser.setBirthDate(user.getBirthDate());
        existingUser.setUserType(user.getUserType());
        return userRepository.save(existingUser);
    }
    @Override
    public void deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }
}

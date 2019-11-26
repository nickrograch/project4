package com.javamentors.service;

import com.javamentors.entity.AppUser;
import com.javamentors.repository.UserRepository;
import com.javamentors.util.ExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public List<AppUser> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public void addUser(AppUser appUser) {

        AppUser checkAppUser = userRepository.findByName(appUser.getName());
        if (checkAppUser != null){
            throw new ExistException("User is already exist");
        }
        else{
            appUser.setPassword(bCryptPasswordEncoder.encode(appUser.getPassword()));
            userRepository.save(appUser);
        }
    }

    @Override
    public void deleteUser(AppUser appUser) {
        userRepository.delete(appUser);
    }

    @Override
    public void editUser(AppUser appUser) {
        AppUser user = appUser;
        if (user.getPassword().length() < 20) {
            user.setPassword(bCryptPasswordEncoder.encode(appUser.getPassword()));
        }
        userRepository.saveAndFlush(user);
    }

    @Override
    public AppUser getUserById(long id) {
        return userRepository.getById(id);
    }
}

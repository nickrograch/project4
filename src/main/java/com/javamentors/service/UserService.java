package com.javamentors.service;

import com.javamentors.entity.AppUser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    List<AppUser> getUsers();

    void addUser(AppUser appUser);

    void deleteUser(AppUser appUser);

    void editUser(AppUser appUser);

    AppUser getUserById(long id);

}
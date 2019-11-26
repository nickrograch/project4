package com.javamentors.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javamentors.entity.AppUser;
import com.javamentors.entity.Role;
import com.javamentors.repository.RoleRepository;
import com.javamentors.repository.UserRepository;
import com.javamentors.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@RestController
@RequestMapping("/rest")
public class RestUserController {

    private final UserService userService;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Autowired
    private RestUserController(UserService userService, RoleRepository roleRepository, UserRepository userRepository){
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/userlist")
    public ResponseEntity<List<AppUser>> listAllUsers(Model model) {
        List<AppUser> appUsers = userService.getUsers();
        model.addAttribute(appUsers);
        if (appUsers.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else {
            return new ResponseEntity<>(appUsers, HttpStatus.OK);
        }
    }

    @PostMapping("/add/{role}")
    public ResponseEntity<?> createUser(@RequestBody AppUser appUser, @PathVariable("role")String role){
        Role userRole = roleRepository.findByName(role);
        appUser.getRoles().add(userRole);
        userService.addUser(appUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/edit/{role}")
    public ResponseEntity<?> updateUser(@RequestBody AppUser appUser, @PathVariable("role") String role) throws IOException {

        updateUser(appUser, role, userRepository, roleRepository, userService);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") String id){
        AppUser getAppUser = userService.getUserById(Long.parseLong(id));
        userService.deleteUser(getAppUser);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    static AppUser updateUser(@ModelAttribute("user") AppUser appUser, String role, UserRepository userRepository, RoleRepository roleRepository, UserService userService) {
        AppUser updateUser = userRepository.getById(appUser.getId());
        updateUser.getRoles().clear();
        if (appUser.getPassword().isEmpty()){
            updateUser.setName(appUser.getName());
            updateUser.setEmail(appUser.getEmail());
        }
        else {
            updateUser.setName(appUser.getName());
            updateUser.setEmail(appUser.getEmail());
            updateUser.setPassword(appUser.getPassword());
        }
        Role userRole = roleRepository.findByName("USER");
        Role adminRole = roleRepository.findByName("ADMIN");
        if (role.contains("USER") && role.contains("ADMIN")) {
            updateUser.getRoles().add(userRole);
            updateUser.getRoles().add(adminRole);
        } else if (role.contains("USER")) {
            updateUser.getRoles().add(userRole);
        } else if (role.contains("ADMIN")) {
            updateUser.getRoles().add(adminRole);
        }
        userService.editUser(updateUser);

        return updateUser;
    }

}

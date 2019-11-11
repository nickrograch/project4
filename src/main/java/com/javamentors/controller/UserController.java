package com.javamentors.controller;

import com.javamentors.entity.AppUser;
import com.javamentors.entity.Role;
import com.javamentors.repository.RoleRepository;
import com.javamentors.repository.UserRepository;
import com.javamentors.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/userlist")
    public String userList(@ModelAttribute("edit") String edit, @ModelAttribute("user") AppUser appUser, Model model) {
        List<AppUser> appUsers = userService.getUsers();
//        for (AppUser appuser : appUsers) {
//            appUser.setPassword(appUser.getPassword().decode());
//        }
        model.addAttribute("users", appUsers);
        return "userList";
    }

    @GetMapping("/add")
    public String addPage() {
        return "add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("user") AppUser appUser, @RequestParam("role") String role) {
        AppUser newAppUser = new AppUser(appUser.getName(), appUser.getPassword(), appUser.getEmail());
        Role userRole = roleRepository.findByName(role);
        newAppUser.getRoles().add(userRole);
        userService.addUser(newAppUser);
        return "redirect:/userlist";
    }

    @PostMapping("/edit")
    public String edit(@RequestParam("id") long id, @RequestParam("name") String name, @RequestParam("role") String role,
                       @RequestParam("password") String password, @RequestParam("email") String email) {
        AppUser editAppUser = userRepository.getById(id);
        editAppUser.getRoles().clear();
        if (password.equals("")){
            editAppUser.setName(name);
            editAppUser.setEmail(email);
        }
        else {
            editAppUser.setName(name);
            editAppUser.setEmail(email);
            editAppUser.setPassword(password);
        }
        Role userRole = roleRepository.findByName("USER");
        Role adminRole = roleRepository.findByName("ADMIN");
        if (role.contains("USER") && role.contains("ADMIN")) {
            editAppUser.getRoles().add(userRole);
            editAppUser.getRoles().add(adminRole);
        } else if (role.contains("USER")) {
            editAppUser.getRoles().add(userRole);
        } else if (role.contains("ADMIN")) {
            editAppUser.getRoles().add(adminRole);
        }
        userService.editUser(editAppUser);
        return "redirect:/userlist";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("id") long id) {
        AppUser getAppUser = userService.getUserById(id);
        userService.deleteUser(getAppUser);
        return "redirect:/userlist";
    }

    @GetMapping("/login/access_denied")
    public String accessDenied() {
        return "accessDenied";
    }
}

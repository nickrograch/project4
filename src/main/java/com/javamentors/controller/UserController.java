package com.javamentors.controller;

import com.javamentors.entity.AppUser;
import com.javamentors.entity.Role;
import com.javamentors.repository.RoleRepository;
import com.javamentors.repository.UserRepository;
import com.javamentors.service.UserService;
import com.javamentors.service.UserServiceImpl;
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

    private final UserService userService;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Autowired
    private UserController(UserService userService, RoleRepository roleRepository, UserRepository userRepository){
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }


    @GetMapping("/userlist")
    public String userList(@ModelAttribute("user") AppUser appUser, Model model) {
//        List<AppUser> appUsers = userService.getUsers();
//        model.addAttribute("users", appUsers);
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
    public String edit(@ModelAttribute("user") AppUser appUser, @RequestParam("role") String role
                      ) {
        RestUserController.updateUser(appUser, role, userRepository, roleRepository, userService);
        return "redirect:/userlist";
    }

    @PostMapping("/delete")
    public String delete(@ModelAttribute("user") AppUser appUser) {
        AppUser getAppUser = userService.getUserById(appUser.getId());
        userService.deleteUser(getAppUser);
        return "redirect:/userlist";
    }

    @GetMapping("/login/access_denied")
    public String accessDenied() {
        return "accessDenied";
    }
}

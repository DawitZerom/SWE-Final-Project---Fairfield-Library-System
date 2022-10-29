package edu.miu.cs.cs425.fairfieldlibrarywebapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import edu.miu.cs.cs425.fairfieldlibrarywebapp.model.User;
import edu.miu.cs.cs425.fairfieldlibrarywebapp.service.RoleService;
import edu.miu.cs.cs425.fairfieldlibrarywebapp.service.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping(value = { "/library/secured/admin", "/secured/admin" })
public class UserMnmtController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @GetMapping(value = { "/usrmgmt/list" })
    public ModelAndView displayUsersList() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("users", userService.getAllUsers());
        mav.setViewName("secured/admin/usrmgmt/list");
        return mav;
    }

    @GetMapping(value = { "/usrmgmt/user/new" })
    public ModelAndView displayNewUserForm() {
        ModelAndView mav = new ModelAndView();
        User user = new User();
        mav.addObject("user", user);
        mav.addObject("roles", roleService.getAllRoles());
        mav.setViewName("secured/admin/usrmgmt/newuser");
        return mav;
    }

    @PostMapping(value = { "/usrmgmt/user/new" })
    public String addNewUser(@Valid @ModelAttribute("user") User user,
            Model model, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "secured/admin/usrmgmt/newuser";
        }
        userService.saveUser(user);
        return "redirect:/library/secured/admin/usrmgmt/list";
    }

    @GetMapping(value = { "/usrmgmt/user/edit/{userId}" })
    public String editUser(@PathVariable Integer userId, Model model) {
        User user = userService.getUserById(userId);
        if (user != null) {
            user.setPassword("");
            model.addAttribute("user", user);
            model.addAttribute("roles", roleService.getAllRoles());
            return "secured/admin/usrmgmt/edituser";
        }
        return "secured/admin/usrmgmt/list";
    }

    @PostMapping(value = { "/usrmgmt/user/edit" })
    public String updateUser(@Valid @ModelAttribute("user") User user,
            Model model, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "secured/admin/usrmgmt/edituser";
        }
        user = userService.saveUser(user);
        return "redirect:/library/secured/admin/usrmgmt/list";
    }
}

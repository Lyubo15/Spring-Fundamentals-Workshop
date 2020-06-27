package com.example.school.web.controllers;

import com.example.school.help.Helper;
import com.example.school.service.models.UserLoginServiceModel;
import com.example.school.service.models.UserRegisterServiceModel;
import com.example.school.service.services.AdminService;
import com.example.school.service.services.HomeworkService;
import com.example.school.service.services.UserService;
import com.example.school.web.models.bindingModels.UserLoginBindingModel;
import com.example.school.web.models.bindingModels.UserRegisterBindingModel;
import com.example.school.web.models.viewModels.UserViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping("/user")
public class UserController {

    private static final String BINDING_RESULT_NAME = "org.springframework.validation.BindingResult.";
    private final UserService userService;
    private final HttpSession session;
    private final ModelMapper modelMapper;
    private final HomeworkService homeworkService;
    private final AdminService adminService;

    @Autowired
    public UserController(UserService userService, HttpSession session, ModelMapper modelMapper, HomeworkService homeworkService, AdminService adminService) {
        this.userService = userService;
        this.session = session;
        this.modelMapper = modelMapper;
        this.homeworkService = homeworkService;
        this.adminService = adminService;
    }

    @GetMapping("/login")
    public String getLogin(Model model) {
//        String isRedirect = Helper.Redirect.redirectAuthenticatedUser(session, "redirect:/");
//        if (isRedirect != null) {
//            return isRedirect;
//        }

        Helper.Model.addAttributes(new LinkedHashMap<>() {{
            put("username", "");
        }}, model);

        return "login";
    }

    @PostMapping("/login")
    public String postLogin(@ModelAttribute UserLoginBindingModel userModel,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {

        Optional<UserLoginServiceModel> user = this.userService.login(userModel);

        if (user.isEmpty()) {
            redirectAttributes.addFlashAttribute("username", userModel.getUsername());
            redirectAttributes.addFlashAttribute("errors", "Invalid username or password.");
            return "redirect:/user/login";
        }

        session.setAttribute("username", user.get().getUsername());
        session.setAttribute("id", user.get().getId());
        session.setAttribute("role", user.get().getRole().getAuthority());
        return "redirect:/";
    }

    @GetMapping("/register")
    public String getRegister(Model model) {

//        String isRedirect = Helper.Redirect.redirectAuthenticatedUser(session, "redirect:/");
//        if (isRedirect != null) {
//            return isRedirect;
//        }
//         if use modelAndView
//        modelAndView.setViewName("register");
//        modelAndView.addAllObjects(model.asMap());
//        modelAndView.setViewName("register");
        Helper.Model.addAttributes(new LinkedHashMap<>() {{
            put("user", new UserRegisterBindingModel());
        }}, model);
        return "register";
    }

    @PostMapping("/register")
    public String postRegister(@Valid @ModelAttribute("user") UserRegisterBindingModel userRegisterBindingModel,
                               BindingResult result,
                               RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(BINDING_RESULT_NAME + "user", result);
            redirectAttributes.addFlashAttribute("user", userRegisterBindingModel);
            return "redirect:/user/register";
        }

        Optional<UserRegisterServiceModel> user = this.userService.register(userRegisterBindingModel);

        if (user.isPresent()) {
            return "redirect:/user/login";
        }

        redirectAttributes.addFlashAttribute("user", userRegisterBindingModel);
        redirectAttributes.addFlashAttribute("error", "This username's already exist");
        return "redirect:/user/register";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/profile")
    public String getProfile(Model model) {
//        String isRedirect = Helper.Redirect.redirectUnAuthenticatedUser(session, "redirect:/");
//        if (isRedirect != null) { return isRedirect; }

        UserViewModel user = this.modelMapper.map(this.userService.getUserByUsername(session.getAttribute("username").toString()), UserViewModel.class);

        model.addAttribute("user", user);
        model.addAttribute("view", Helper.Return.returnNamesOfExerciseOrHomework(session, this.homeworkService, user, this.adminService));
        return "profile";
    }
}

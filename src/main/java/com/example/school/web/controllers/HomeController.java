package com.example.school.web.controllers;

import com.example.school.help.Helper;
import com.example.school.service.services.AdminService;
import com.example.school.service.services.UserService;
import com.example.school.web.models.viewModels.ExerciseViewModel;
import com.example.school.web.models.viewModels.UserViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    private final UserService userService;
    private final AdminService adminService;

    @Autowired
    public HomeController(UserService userService, AdminService adminService) {
        this.userService = userService;
        this.adminService = adminService;
    }

    @GetMapping("/")
    public String home(Model model, HttpSession session) {//        ModelAndView modelAndView = Helper.Redirect.redirectUnAuthenticatedUser(session, "index", model);
//        if (modelAndView != null) { return modelAndView; }
//          in the end
//        model.setViewName("home");
//        return model;

        String isRedirect = Helper.Redirect.redirectUnAuthenticatedUser(session, "index");
        if (isRedirect != null) { return isRedirect; }
        List<ExerciseViewModel> exerciseServiceModels = (List<ExerciseViewModel>) Helper.Converter.convertListFromServiceModelToView(this.adminService.getAllExercise(), ExerciseViewModel.class);
        List<UserViewModel> userServiceModels = (List<UserViewModel>) Helper.Converter.convertListFromServiceModelToView(this.userService.getFirstThreeUserOrderByAvgGradesAndUsername(), UserViewModel.class);

        Helper.Model.addAttributes(new LinkedHashMap<>() {{
            put("users", userServiceModels);
            put("exercises", exerciseServiceModels);
        }}, model);

        return "home";
    }
}

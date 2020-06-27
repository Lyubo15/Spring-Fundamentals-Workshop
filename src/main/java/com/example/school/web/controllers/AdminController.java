package com.example.school.web.controllers;

import com.example.school.help.Helper;
import com.example.school.service.services.AdminService;
import com.example.school.service.services.UserService;
import com.example.school.web.models.bindingModels.ExerciseCreateBindingModel;
import com.example.school.web.models.bindingModels.RoleBindingModel;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private static final String BINDING_RESULT_NAME = "org.springframework.validation.BindingResult.";

    private final AdminService adminService;
    private final UserService userService;
    private final HttpSession session;
    private final ModelMapper modelMapper;

    @Autowired
    public AdminController(AdminService adminService, UserService userService, HttpSession session, ModelMapper modelMapper) {
        this.adminService = adminService;
        this.userService = userService;
        this.session = session;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add-exercise")
    public String getExercise(Model model) { // <- ModelAndView
//        ModelAndView nonAdmin = Helper.Redirect.redirectNonAdminOrUnAuthenticated(session, "redirect:/", model);
//        if (nonAdmin != null) { return nonAdmin; }
//     in the end ->  model.setViewName("exercise-add");

//        String isRedirect = Helper.Redirect.redirectNonTeacherOrUnAuthenticated(session, "redirect:/");
//        if (isRedirect != null) { return isRedirect; }

        Helper.Model.addAttributes(new LinkedHashMap<>() {{
            put("exercise", new ExerciseCreateBindingModel());
        }}, model);

        return "exercise-add";
    }

    @PostMapping("/add-exercise")
    public String postExercise(@Valid @ModelAttribute("exercise") ExerciseCreateBindingModel exerciseCreateBindingModel,
                               BindingResult result,
                               RedirectAttributes redirectAttributes) {//  modelAndView.setViewName("exercise-add"); <- first if
//   modelAndView.setViewName("redirect:/"); <- second if
//   return modelAndView; <- end

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(BINDING_RESULT_NAME + "exercise", result);
            return "exercise-add";
        }

        this.adminService.createExercise(exerciseCreateBindingModel);
        return "redirect:/";
    }

    @GetMapping("/add-role")
    public String getRole(Model model) {//        ModelAndView nonAdmin = Helper.Redirect.redirectNonAdminOrUnAuthenticated(session, "redirect:/", model);
//        if (nonAdmin != null) {
//            return nonAdmin;
//        }
//        end
//    model.setViewName("role-add");
//        return model;

//        String isRedirect = Helper.Redirect.redirectNonTeacherOrUnAuthenticated(session, "redirect:/");
//        if (isRedirect != null) { return isRedirect; }

        List<UserViewModel> userViewModels =
                this.userService
                .getAllUsersWithoutCurrent(session.getAttribute("username").toString())
                .stream()
                .map(userServiceModel -> this.modelMapper.map(userServiceModel, UserViewModel.class))
                .collect(Collectors.toList());

        Helper.Model.addAttributes(new LinkedHashMap<>() {{
            put("isWrongValue", false);
            put("users", userViewModels);
        }}, model);

        model.addAttribute("role", new RoleBindingModel());

        return "role-add";
    }

    @PostMapping("/add-role")
    public String postRole(@Valid @ModelAttribute("role") RoleBindingModel roleBindingModel,
                           BindingResult result,
                           RedirectAttributes redirectAttributes) {

        if (result.hasErrors()){
            redirectAttributes.addFlashAttribute("isWrongValue", true);
            return "redirect:/admin/add-role";
        }

        this.adminService.changeUserRole(roleBindingModel);
        return "redirect:/";
    }

}

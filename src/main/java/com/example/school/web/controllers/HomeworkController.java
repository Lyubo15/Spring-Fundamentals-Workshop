package com.example.school.web.controllers;

import com.example.school.help.Helper;
import com.example.school.service.models.HomeworkServiceModel;
import com.example.school.service.services.AdminService;
import com.example.school.service.services.CommentService;
import com.example.school.service.services.HomeworkService;
import com.example.school.web.models.bindingModels.CommentBindingModel;
import com.example.school.web.models.bindingModels.HomeworkBindingModel;
import com.example.school.web.models.viewModels.ExerciseViewModel;
import com.example.school.web.models.viewModels.HomeworkViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


@Controller
public class HomeworkController {

    private static final String BINDING_RESULT_NAME = "org.springframework.validation.BindingResult.";

    private final HomeworkService homeworkService;
    private final AdminService adminService;
    private final ModelMapper modelMapper;
    private final HttpSession session;
    private final CommentService commentService;


    @Autowired
    public HomeworkController(HomeworkService homeworkService, AdminService adminService, ModelMapper modelMapper, HttpSession session, CommentService commentService) {
        this.homeworkService = homeworkService;
        this.adminService = adminService;
        this.modelMapper = modelMapper;
        this.session = session;
        this.commentService = commentService;
    }

    @GetMapping("/add-homework")
    public String getHomeworkView(Model model) { // ModelAndView model <- case: ModelAndView
//        in case ModelAndView
//        ModelAndView modelAndView = Helper.Redirect.redirectUnAuthenticatedUser(session, "redirect:/user/login", model);
//        if (modelAndView != null) {
//            return modelAndView;
//        }
//        model.addObject("exercises", exerciseServiceModels)
//          model.setViewName("homework-add");
//           return model;

//        String isRedirect = Helper.Redirect.redirectNonStudentOrUnAuthenticated(session, "redirect:/");
//        if (isRedirect != null) {
//            return isRedirect;
//        }

        List<ExerciseViewModel> exerciseServiceModels =
                (List<ExerciseViewModel>) Helper.Converter.convertListFromServiceModelToView(this.adminService.getAllExercise(), ExerciseViewModel.class);

        model.addAttribute("exercises", exerciseServiceModels);

        Helper.Model.addAttributes(new LinkedHashMap<>() {{
            put("homework", new HomeworkBindingModel());
            put("exercises", new ArrayList<>());
        }}, model); // modelAndView

        return "homework-add";
    }

    @PostMapping("/add-homework")
    public String postHomework(@Valid @ModelAttribute("homework") HomeworkBindingModel homeworkBindingModel,
                               BindingResult result,
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {//       modelAndView.setViewName("redirect:/");
// in first if -> modelAndView.setViewName("redirect:/add-homework");
// in second if -> modelAndView.setViewName("redirect:/add-homework");
// in the end ->  return modelAndView;

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(BINDING_RESULT_NAME + "homework", result);
            redirectAttributes.addFlashAttribute("homework", homeworkBindingModel);
            return "redirect:/add-homework";
        }

        boolean isCreate = this.homeworkService.createHomework(
                LocalDateTime.now(),
                session.getAttribute("username").toString(),
                homeworkBindingModel.getGitAddress(),
                homeworkBindingModel.getExercise());

        if (!isCreate) {
            redirectAttributes.addFlashAttribute("homeworkExcise", true);
            return "redirect:/add-homework";
        }

        return "redirect:/";
    }

    @GetMapping("/check-homework")
    public String getHomeworkCheck(Model model) { // ModelAndView

//        ModelAndView modelAndView = Helper.Redirect.redirectUnAuthenticatedUser(session, "redirect:/user/login"); <- like end param modelAndView
//        if (modelAndView != null) {
//            return modelAndView;
//        }
//      model.setViewName("homework-check");

//        String isRedirect = Helper.Redirect.redirectNonTeacherOrUnAuthenticated(session, "redirect:/");
//        if (isRedirect != null) {
//            return isRedirect;
//        }

        if (!this.homeworkService.isHasHomework()) {
            HomeworkViewModel h = new HomeworkViewModel();
            h.setChecked(true);
            h.setId(0);

            Helper.Model.addAttributes(new LinkedHashMap<>() {{
                put("msg", "There aren't homeworks");
                put("homework", h);
                put("comment", new CommentBindingModel());
            }}, model);
        } else {
            HomeworkViewModel homework = this.modelMapper.map(this.homeworkService.getRandomHomework(), HomeworkViewModel.class);
            session.setAttribute("homework", homework);

            Helper.Model.addAttributes(new LinkedHashMap<>() {{
                put("comment", new CommentBindingModel());
                put("homework", homework);
            }}, model);
        }

        return "homework-check";
    }

    @PostMapping("/comment")
    public String postComment(@Valid @ModelAttribute("comment") CommentBindingModel commentBindingModel,
                              BindingResult result,
                              RedirectAttributes redirectAttributes) {//        modelAndView.setViewName("redirect:/");
//        in first if    modelAndView.setViewName("redirect:/check-homework");
//       in the end return modelAndView

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("comment", commentBindingModel);
            redirectAttributes.addFlashAttribute(BINDING_RESULT_NAME + "comment", result);
            return "redirect:/check-homework";
        } else {
            this.commentService.postComment(
                    this.modelMapper.map(session.getAttribute("homework"), HomeworkServiceModel.class),
                    commentBindingModel,
                    session.getAttribute("username").toString());
        }

        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id) {
        //        ModelAndView modelAndView = Helper.Redirect.redirectNonAdminOrUnAuthenticated(session, "redirect:/user/login", model);
//        if (modelAndView != null) {
//            return modelAndView;
//        }
//        model.setViewName("redirect:/");

//        String isRedirect = Helper.Redirect.redirectNonTeacherOrUnAuthenticated(session, "redirect:/");
//        if (isRedirect != null) {
//            return isRedirect;
//        }

        this.homeworkService.deleteById(id);
        return "redirect:/";
    }

}

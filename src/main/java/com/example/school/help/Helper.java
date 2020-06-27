package com.example.school.help;

import com.example.school.service.services.AdminService;
import com.example.school.service.services.HomeworkService;
import com.example.school.web.models.viewModels.UserViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class Helper {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static class Model {

        public static void addAttributes(Map<String, Object> attrs, org.springframework.ui.Model model) {
            attrs.forEach((key, value) -> {
                if (model.getAttribute(key) == null) {
                    model.addAttribute(key, value);
                }
            });
        }
    }

    public static class Return {

        public static List<?> returnNamesOfExerciseOrHomework(HttpSession session, HomeworkService homeworkService, UserViewModel user, AdminService adminService) {
            if (session.getAttribute("role").toString().equals("TEACHER")) {
                return adminService.getAllExercisesNameByUser(user);
            }
            return homeworkService.getAllHomeworksNameByUser(user);
        }
    }

    public static class Redirect {

        public static String redirectUnAuthenticatedUser(HttpSession session, String path) {
            if (isUserNotLogged(session)) {
                return path;
            }
            return null;
        }

        public static String redirectAuthenticatedUser(HttpSession session, String path) {
            if (!isUserNotLogged(session)) {
                return path;
            }
            return null;
        }

        public static String redirectNonTeacherOrUnAuthenticated(HttpSession session, String path) {
            if (redirectUnAuthenticatedUser(session, "redirect:/user/login") != null) {
                return "redirect:/user/login";
            }

            if (!session.getAttribute("role").equals("TEACHER")) {
                return path;
            }
            return null;
        }

        public static String redirectNonStudentOrUnAuthenticated(HttpSession session, String path) {
            if (redirectUnAuthenticatedUser(session, "redirect:/user/login") != null) {
                return "redirect:/user/login";
            }

            if (!session.getAttribute("role").equals("STUDENT")) {
                return path;
            }
            return null;
        }

        private static boolean isUserNotLogged(HttpSession session) {
            return session.getAttribute("username") == null;
        }
    }

    public static class Converter {

        public static List<?> convertListFromServiceModelToView(List<?> serviceModels, Class<?> view) {
            return serviceModels
                    .stream()
                    .map(companyServiceModel -> modelMapper.map(companyServiceModel, view))
                    .collect(Collectors.toList());
        }

        public static Object convertFromServiceModelToView(Object serviceModels, Class<?> view) {
            return modelMapper.map(serviceModels, view);
        }

    }
}

package com.example.school.web.interseptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {

    private final NonLoggedUserInterceptor nonLoggedUserInterceptor;
    private final LoggedUserTeacherInterceptor loggedUserTeacherInterceptor;
    private final LoggedUserStudentInterceptor loggedUserStudentInterceptor;

    @Autowired
    public InterceptorConfiguration(NonLoggedUserInterceptor nonLoggedUserInterceptor, LoggedUserTeacherInterceptor loggedUserTeacherInterceptor, LoggedUserStudentInterceptor loggedUserStudentInterceptor) {
        this.nonLoggedUserInterceptor = nonLoggedUserInterceptor;
        this.loggedUserTeacherInterceptor = loggedUserTeacherInterceptor;
        this.loggedUserStudentInterceptor = loggedUserStudentInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(nonLoggedUserInterceptor).addPathPatterns("/user/login", "/user/register");
        registry.addInterceptor(loggedUserTeacherInterceptor).addPathPatterns("/admin/add-exercise", "/admin/add-role", "/check-homework", "/delete/{id}");
        registry.addInterceptor(loggedUserStudentInterceptor).addPathPatterns("/add-homework");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/css/**");
    }
}

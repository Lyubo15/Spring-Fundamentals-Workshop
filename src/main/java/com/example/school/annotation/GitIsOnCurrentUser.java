package com.example.school.annotation;

import com.example.school.annotation.impl.IsGitOnCurrentUser;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
@Constraint( validatedBy = IsGitOnCurrentUser.class)
@Documented
public @interface GitIsOnCurrentUser {

    String message() default "{org.hibernate.validator.constraints.GitIsOnCurrentUser.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

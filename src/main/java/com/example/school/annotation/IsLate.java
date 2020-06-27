package com.example.school.annotation;

import com.example.school.annotation.impl.Late;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
@Constraint(validatedBy = Late.class)
@Documented
public @interface IsLate {

    String message() default "{org.hibernate.validator.constraints.IsLate.message}";


    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

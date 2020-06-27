package com.example.school.annotation;

import com.example.school.annotation.impl.IsValidValue;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
@Constraint( validatedBy = IsValidValue.class)
@Documented
public @interface NotValue {

    String message() default "{org.hibernate.validator.constraints.NotValue.message}";

    String wrongValue();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}



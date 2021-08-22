package com.egs.shop.validator;

import com.egs.shop.model.constant.ValidationMessages;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordMatchesValidator.class)
@Documented
public @interface PasswordMatches {

    String message() default ValidationMessages.PasswordMatches;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}

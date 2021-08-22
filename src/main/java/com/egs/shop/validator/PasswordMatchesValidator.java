package com.egs.shop.validator;

import org.springframework.beans.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        try
        {
            final Object password = BeanUtils.getPropertyDescriptor((Class<?>) obj, "password");
            final Object confirmPassword = BeanUtils.getPropertyDescriptor((Class<?>) obj, "confirmPassword");

            return password != null && password.equals(confirmPassword);
        }
        catch (final Exception ignore)
        {
            // ignore
        }
        return true;
    }
}

package net.wenzuo.mono.web.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

/**
 * @author Catch
 * @since 2021-04-29
 */
public class PhoneValidator implements ConstraintValidator<Phone, CharSequence> {

    private Pattern pattern;

    @Override
    public void initialize(Phone constraintAnnotation) {
        pattern = Pattern.compile("^1[3-9]\\d{9}$");
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        return value == null || pattern.matcher(value).matches();
    }

}



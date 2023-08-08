package com.example.Module2.Validation;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = OperationValidator.class)
public @interface ValidOperation {
    String message() default "Invalid Operation";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

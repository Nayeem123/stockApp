package com.example.Module2.Validation;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class OperationValidator implements ConstraintValidator<ValidOperation,String> {
    @Override
    public boolean isValid(String operation, ConstraintValidatorContext constraintValidatorContext) {
        List<String> operations= Arrays.asList("UPDATE","CREATE","DELETE");
        return operations.contains(operation);
        //return (EnumUtils.isValidEnum(ApplicationConstants.OPERATION.class,operation.toUpperCase()));
    }
}

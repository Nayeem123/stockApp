//package com.example.Module2.Validation;
//
//import com.example.Module2.config.ApplicationConstants;
//import org.apache.commons.lang3.EnumUtils;
//
//import javax.validation.ConstraintValidator;
//import javax.validation.ConstraintValidatorContext;
//
//public class OperationValidator implements ConstraintValidator<ValidOperation,String> {
//    @Override
//    public boolean isValid(String operation, ConstraintValidatorContext constraintValidatorContext) {
//        return (EnumUtils.isValidEnum(ApplicationConstants.OPERATION.class,operation.toUpperCase()));
//    }
//}


package com.example.WebProject.validator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.example.WebProject.model.FluteInfo;


@Component
public class EditFluteValidator implements Validator {
 
   
 
    // Validator này chỉ dùng để kiểm tra đối với FluteInfo
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == FluteInfo.class;
    }
 
    @Override
    public void validate(Object target, Errors errors) {
    	
 
        // Kiểm tra các trường (field) của CustomerForm.
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty.fluteInfo.name");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "gia", "NotEmpty.fluteInfo.gia");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "soluong", "NotEmpty.fluteInfo.soluong");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "giamgia", "NotEmpty.fluteInfo.giamgia");
 
  
    }
 
}

package com.example.WebProject.validator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.example.WebProject.model.AccessoryInfo;


@Component
public class EditAccessoryValidator implements Validator {
 
   
 
    // Validator này chỉ dùng để kiểm tra đối với AccessoryInfo
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == AccessoryInfo.class;
    }
 
    @Override
    public void validate(Object target, Errors errors) {
    	
 
        // Kiểm tra các trường (field) của CustomerForm.
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty.accessoryInfo.name");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "gia", "NotEmpty.accessoryInfo.gia");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "soluong", "NotEmpty.accessoryInfo.soluong");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "giamgia", "NotEmpty.accessoryInfo.giamgia");
 
  
    }
 
}
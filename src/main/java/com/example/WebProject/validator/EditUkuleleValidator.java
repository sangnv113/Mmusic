
package com.example.WebProject.validator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.example.WebProject.model.UkuleleInfo;


@Component
public class EditUkuleleValidator implements Validator {
 
   
 
    // Validator này chỉ dùng để kiểm tra đối với UkuleleInfo
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == UkuleleInfo.class;
    }
 
    @Override
    public void validate(Object target, Errors errors) {
    	
 
        // Kiểm tra các trường (field) của CustomerForm.
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty.ukuleleInfo.name");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "gia", "NotEmpty.ukuleleInfo.gia");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "soluong", "NotEmpty.ukuleleInfo.soluong");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "giamgia", "NotEmpty.ukuleleInfo.giamgia");
 
  
    }
 
}
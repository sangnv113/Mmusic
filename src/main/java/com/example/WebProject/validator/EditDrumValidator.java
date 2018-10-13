
package com.example.WebProject.validator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.example.WebProject.model.DrumInfo;


@Component
public class EditDrumValidator implements Validator {
 
   
 
    // Validator này chỉ dùng để kiểm tra đối với DrumInfo
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == DrumInfo.class;
    }
 
    @Override
    public void validate(Object target, Errors errors) {
    	
 
        // Kiểm tra các trường (field) của CustomerForm.
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty.drumInfo.name");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "gia", "NotEmpty.drumInfo.gia");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "soluong", "NotEmpty.drumInfo.soluong");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "giamgia", "NotEmpty.drumInfo.giamgia");
 
  
    }
 
}
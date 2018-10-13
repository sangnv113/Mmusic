
package com.example.WebProject.validator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.example.WebProject.model.PianoInfo;


@Component
public class EditPianoValidator implements Validator {
 
   
 
    // Validator này chỉ dùng để kiểm tra đối với PianoInfo
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == PianoInfo.class;
    }
 
    @Override
    public void validate(Object target, Errors errors) {
    	
 
        // Kiểm tra các trường (field) của CustomerForm.
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty.pianoInfo.name");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "gia", "NotEmpty.pianoInfo.gia");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "soluong", "NotEmpty.pianoInfo.soluong");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "giamgia", "NotEmpty.pianoInfo.giamgia");
 
  
    }
 
}
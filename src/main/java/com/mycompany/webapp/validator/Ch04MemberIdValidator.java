package com.mycompany.webapp.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.mycompany.webapp.dto.Ch04Member;

public class Ch04MemberIdValidator implements Validator{
	private static final Logger logger = LoggerFactory.getLogger(Ch04MemberIdValidator.class);
	@Override
	public boolean supports(Class<?> clazz) {
		logger.info("mid supports 실행");
		boolean check = Ch04Member.class.isAssignableFrom(clazz);
		return check;
	}

	@Override
	public void validate(Object target, Errors errors) {
		logger.info("mid validate 실행");
		
		Ch04Member member = (Ch04Member) target;
		
		// mid 검사
		if(member.getMid()==null || member.getMid().trim().equals("")) {
			errors.rejectValue("mid","errors.mid.required");
		}else if(member.getMid().length() < 4){
			errors.rejectValue("mid","errors.mid.minlength",new Object[] {4}, ""); 
		}
	}
	
}

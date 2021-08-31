package com.mycompany.webapp.validator;

import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.mycompany.webapp.dto.Ch04Member;

public class Ch04MemberTelValidator implements Validator {
	private static final Logger logger = LoggerFactory.getLogger(Ch04MemberTelValidator.class);

	@Override
	public boolean supports(Class<?> clazz) {
		logger.info("mtel supports 실행");
		boolean check = Ch04Member.class.isAssignableFrom(clazz);
		return check;
	}

	@Override
	public void validate(Object target, Errors errors) {
		logger.info("mtel validate 실행");

		Ch04Member member = (Ch04Member) target;

		// mtel 검사
		if (member.getMtel() == null || member.getMtel().trim().equals("")) {
			errors.rejectValue("mtel", "errors.mtel.required"); 
		} else {
			String regExp = "^\\d{3}-\\d{3,4}-\\d{4}$"; 
			Pattern pattern = Pattern.compile(regExp);
			if (!pattern.matcher(member.getMtel()).matches()) {
				errors.rejectValue("mtel", "errors.mtel.invalid");
			}
		}
	}

}

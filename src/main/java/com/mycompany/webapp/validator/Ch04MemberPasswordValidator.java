package com.mycompany.webapp.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.mycompany.webapp.dto.Ch04Member;

public class Ch04MemberPasswordValidator implements Validator {
	private static final Logger logger = LoggerFactory.getLogger(Ch04MemberPasswordValidator.class);

	@Override
	public boolean supports(Class<?> clazz) {
		logger.info("mpassword supports 실행");
		boolean check = Ch04Member.class.isAssignableFrom(clazz);
		return check;
	}

	@Override
	public void validate(Object target, Errors errors) {
		logger.info("mpassword validate 실행");

		Ch04Member member = (Ch04Member) target;

		// mpassword 검사
		if (member.getMpassword() == null || member.getMpassword().trim().equals("")) {
			errors.rejectValue("mpassword", "errors.mpassword.required");
		} else if (member.getMpassword().length() < 8) {
			errors.rejectValue("mpassword", "errors.mpassword.minlength", new Object[] { 8 }, "");
		}
	}

}

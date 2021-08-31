package com.mycompany.webapp.validator;

import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.mycompany.webapp.dto.Ch04Member;

public class Ch04MemeberJoinFormValidator implements Validator {
	
	private static final Logger logger = LoggerFactory.getLogger(Ch04MemeberJoinFormValidator.class);

	@Override
	public boolean supports(Class<?> clazz) {
		// 유효성 검사가 가능하면 true, 아니면 false
		// supports가 true를 반환해야, validate()가 호출된다.
		logger.info("supports 실행");
		boolean check = Ch04Member.class.isAssignableFrom(clazz); // 파리미터로 오는 객체가 Ch04Member에 대입할 수 있는지 확인
		// 파라미터가 최소한 자식 클래스여야 한다.
		return check;
	}

	@Override
	public void validate(Object target, Errors errors) {
		logger.info("validate 실행");
		Ch04Member member =(Ch04Member) target; // 유효성 검사를 통과했으니, 강제 형변환 가능
		
		// mid 검사
		if(member.getMid()==null || member.getMid().trim().equals("")) {
			errors.rejectValue("mid","errors.mid.required"); // 만든 메시지에서 정의한 것을 참고한다. (errorCode, errorCode)
		}else if(member.getMid().length() < 4){
			errors.rejectValue("mid","errors.mid.minlength",new Object[] {4}, ""); // (errorCode, errorCode,Object[] errorArgs, defaultMessage)
		}
		
		// mpassword 검사
		if(member.getMpassword()==null || member.getMpassword().trim().equals("")) {
			errors.rejectValue("mpassword","errors.mpassword.required"); // 만든 메시지에서 정의한 것을 참고한다. (errorCode, errorCode)
		}else if(member.getMpassword().length() < 8){
			errors.rejectValue("mpassword","errors.mpassword.minlength",new Object[] {8}, ""); 
		}
		
		// memail 검사
		if(member.getMemail()==null || member.getMemail().trim().equals("")) {
			errors.rejectValue("memail","errors.memail.required"); // 만든 메시지에서 정의한 것을 참고한다. (errorCode, errorCode)
		}else{
			String regExp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
			Pattern pattern = Pattern.compile(regExp);
			if(!pattern.matcher(member.getMemail()).matches()) {
				errors.rejectValue("memail","errors.memail.invalid"); 
			}
		}
		
		//mtel 검사
		if(member.getMtel()==null || member.getMtel().trim().equals("")) {
			errors.rejectValue("mtel","errors.mtel.required"); // 만든 메시지에서 정의한 것을 참고한다. (errorCode, errorCode)
		}else{
			String regExp = "^\\d{3}-\\d{3,4}-\\d{4}$"; //     \\ : 역슬래쉬 1개
			Pattern pattern = Pattern.compile(regExp);
			if(!pattern.matcher(member.getMtel()).matches()) {
				errors.rejectValue("mtel","errors.mtel.invalid"); 
			}
		}
	}
}

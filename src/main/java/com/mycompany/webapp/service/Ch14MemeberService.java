package com.mycompany.webapp.service;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mycompany.webapp.dao.Ch14MemberDao;
import com.mycompany.webapp.dto.Ch14Member;

@Service
public class Ch14MemeberService {

	private static final Logger logger = LoggerFactory.getLogger(Ch14MemeberService.class);
	
	// 열거 타입 선언
	public enum JoinResult {
		SUCCESS, FAIL, DUPLICATED
	}
	
	public enum LoginResult {
		SUCCESS, FAIL, WRONG_ID, WRONG_PASSWORD
	}
	
	@Resource
	private Ch14MemberDao memberDao;
	
	// 회원 가입을 처리하는 비지니스 메소드
	public JoinResult join(Ch14Member member) {
		
		try {
			// 이미 가입된 아이디인지 확인
			Ch14Member dbMember = memberDao.selectByMid(member.getMid());

			// DB에 회원 정보를 저장
			if (dbMember == null) {
				try {
					memberDao.insert(member);
					return JoinResult.SUCCESS;
				} catch (Exception e) {
					return JoinResult.FAIL;
				}

			} else{
				return JoinResult.DUPLICATED;
			}
		}catch(Exception e) {
			e.printStackTrace();
			return JoinResult.FAIL;
		}
	}
	
	public LoginResult login(Ch14Member member){
		try {
			Ch14Member dbMember = memberDao.selectByMid(member.getMid());
			if(dbMember == null) {
				return LoginResult.WRONG_ID;
			}else {
				if(dbMember.getMpassword().equals(member.getMpassword())) {
					return LoginResult.SUCCESS;
				}else {
					return LoginResult.WRONG_PASSWORD;
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
			return LoginResult.FAIL;
		}
	}
}

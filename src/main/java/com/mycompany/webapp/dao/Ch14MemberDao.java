package com.mycompany.webapp.dao;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.mycompany.webapp.dto.Ch14Member;

@Repository
public class Ch14MemberDao {
	
	private static final Logger logger = LoggerFactory.getLogger(Ch14MemberDao.class);

	@Resource SqlSessionTemplate sqlSessionTemplate;
	
	public void insert(Ch14Member member) {
		// mybatis는 try ~catch를 구현하지 않게 한다. runtimeException으로 예외를 발생시킨다. 
		// runtimeException 에러가 발생할 수 있는데, 이 예외를 service에서 잡는다. 
		// 절대 spring에선 dao 예외처리를 하지 않는다 -> transaction 처리를 못하게된다.
		sqlSessionTemplate.insert("member.insert", member); // mapper의namespace.namespace안의id값
	}

	public Ch14Member selectByMid(String mid) {
		return sqlSessionTemplate.selectOne("member.selectByMid", mid);
	}
}

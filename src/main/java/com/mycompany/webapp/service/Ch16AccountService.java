package com.mycompany.webapp.service;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.mycompany.webapp.dao.Ch16AccountDao;
import com.mycompany.webapp.dto.Ch16Account;
import com.mycompany.webapp.exeception.Ch16NotFoundAccountException;

@Service
public class Ch16AccountService {
	private static final Logger logger = LoggerFactory.getLogger(Ch16AccountService.class);
	
	public enum TransferResult{
		SUCCESS,
		FAIL_NOT_FOUND_ACCOUNT,
		FAIL_NOT_ENOUGH_BALANCE
	}

	@Resource
	private Ch16AccountDao accountDao;

	@Resource
	private TransactionTemplate transactionTemplate;
	
	public List<Ch16Account> getAccounts() {
		logger.info("실행");
		List<Ch16Account> accounts = accountDao.selectAll();
		return accounts;
	}
	
	// 프로그래밍 방식 - transactionTemplate.execute(new TransactionCallback<String>(){}) 사용하고, .setsetRollbackOnly() 실행
	// 비지니스 로직(서비스)을 실행할 때 리턴하는 값에 따라 컨트롤러가 작업을 처리하는 경우
	public TransferResult transfer1(int fromAno, int toAno, int amount) {
		logger.info("transfer1 실행");
		
		String result = transactionTemplate.execute(new TransactionCallback<String>() { // 익명 자식 객체 생성 - class를 상속해서, 새로운 매소드를 구현하거나, 구현된 메소드를 오버라이드한다.
			@Override
			public String doInTransaction(TransactionStatus status) {
				
				try {
					//출금하기
					Ch16Account fromAccount = accountDao.selectByAno(fromAno);
					fromAccount.setBalance(fromAccount.getBalance()-amount);
					accountDao.updateBalance(fromAccount);
					
					//예금하기
					Ch16Account toAccount = accountDao.selectByAno(toAno);
					toAccount.setBalance(toAccount.getBalance()+amount);
					accountDao.updateBalance(toAccount);
					
					return "success";
				}catch(Exception e) {
					status.setRollbackOnly(); // 트랜잭션 작업을 모두 취소
					return "fail";
				}
				
			}
		});
		
		if(result.equals("success")) {
			return TransferResult.SUCCESS; // 컨트롤러로 리턴하기
		}else {
			//throw new Ch16NotFoundAccountException("계좌가 존재하지 않습니다.");
			return TransferResult.FAIL_NOT_FOUND_ACCOUNT;  // 컨트롤러로 리턴하기
		}
	}
	

	// 선언적 방식 - @Transactional 원하는 예외를 작성하고 싶으면 try~catch runtimeError를 던져줘야한다.
	// try~catch 없으면 예외가 발생하긴하는데, 다른 예외가 발생함.
	// 선언적방식은 예외가 발생하기 때문에, 항상 리턴 값을 컨트롤러로 리턴할 수 없다.
	@Transactional
	public void transfer2(int fromAno, int toAno, int amount) {
		logger.info("transfer2 실행");
		
		try {
			//출금하기
			Ch16Account fromAccount = accountDao.selectByAno(fromAno);
			fromAccount.setBalance(fromAccount.getBalance()-amount);
			accountDao.updateBalance(fromAccount);
			
			//예금하기
			Ch16Account toAccount = accountDao.selectByAno(toAno);
			toAccount.setBalance(toAccount.getBalance()+amount);
			accountDao.updateBalance(toAccount);
			
		}catch(Exception e) {
			throw new Ch16NotFoundAccountException("계좌가 존재하지 않습니다.");
		}
	}
}

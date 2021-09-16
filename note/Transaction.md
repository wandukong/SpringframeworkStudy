# Transaction
> 데이터베이스의 상태를 변화시키기 위해 수행하는 작업의 단위
- all or nothing 
	-  작업들이 모두 성공하든지 실패해야한다.
	-  일부만 성공해서는 안된다.
- 스프링에서 transaction 구현은 프로그래밍 방식과 선언적 방식이 있다.


## ⚙환경 설정

```xml
<bean id="transactionManager"	
	class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
	<property name="dataSource" ref="dataSource"/>	
</bean>

<!-- 프로그래밍 방식으로 처리를 하기위해 필요한 객체 설정 -->
<bean id="transactionTemplate"
	class="org.springframework.transaction.support.TransactionTemplate">
	<property name="transactionManager" ref="transactionManager"/>
</bean>

<!-- 선언적 방식(annotation)으로 처리를 하기위해 필요한 객체 설정 -->
<tx:annotation-driven transaction-manager="transactionManager"/>
```

## 💻프로그래밍 방식
> 	비지니스 로직(서비스)을 실행할 때, **리턴하는 값에 따라 컨트롤러가 작업을 처리**하는 경우에 사용한다.
- **TransactionTemplate** 객체를 사용한다.
	-  **transactionTemplate.execute(new TransactionCallback<리턴타입>(){})**
	-  TransactionCallback 인터페이스를 구현해서, **doInTransaction()** 메소드를 오버라이드한다.
	- 예외가 발생하면, TransactionStatus 객체의 **setsetRollbackOnly()** 메소드를 사용하여 롤백한다.
	-  .execute()의 리턴 값을 사용하여, 컨트롤러로 리턴할 값을 처리해준다.
```java

public TransferResult transfer1(int fromAno, int toAno, int amount) {
	
	String result = transactionTemplate.execute(new TransactionCallback<String>() { 
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
				status.setRollbackOnly(); // 예외가 발생하면 트랜잭션 작업을 모두 취소
				return "fail";
			}
		}
	});
	
	// 컨트롤러로 리턴하기
	if(result.equals("success")) {
		return TransferResult.SUCCESS;
	}else {
		return TransferResult.FAIL_NOT_FOUND_ACCOUNT;  
	}
}
```

## 🧸선언적 방식
> 컨트롤러에 반환하여 예외를 처리하지 않고, 비지니스 로직에서 예외를 처리할 경우 사용한다.
- 비지니스 로직 메소드에 **@Transactional** 를 붙인다.
- 선언적 방식은 비지니스 로직 안에서 예외가 발생하기 때문에, 컨트롤러로 값을 리턴할 수 없다.
- 원하는 예외를 발생하고 싶으면 try~catch 구문을 사용한다.
	- try~catch 없으면 예외가 발생하긴 하지만, 다른 예외가 발생한다.

```java
@Transactional
public void transfer2(int fromAno, int toAno, int amount) {

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
```
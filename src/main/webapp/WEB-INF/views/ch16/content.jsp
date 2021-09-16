<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/common/header.jsp"%>

<div class="card m-2">
	<div class="card-header">트랜잭션 처리</div>
	<div class="card-body">
		<div class="card">
			<div class="card-header">계좌 현황</div>
			<div class="card-body">
				<table style="width:auto" class="table table-sm table-bordered">
	               <tr>
	                  <th style="width:50px">번호</th>
	                  <th style="width:100px">소유주</th>
	                  <th style="width:200px">잔고</th>
	               </tr>
	               <c:forEach var="account" items="${list}">
	                  <tr>
	                     <td>${account.ano}</td>
	                     <td>${account.owner}</td>
	                     <td style="text-align:right;">${account.balance}</td>
	                  </tr>
	               </c:forEach>
	            </table>
			</div>
		</div>
	
		<div class="card">
			<div class="card-header">프로그래밍 방식</div>
			<div class="card-body">
				<form id="accountTransferForm1" method="post" action="transaction1">
				<c:if test="${transferError != null}">
					<div class="alert alert-danger" role="alert">
						${transferError}
					</div>
				</c:if>
					<table style="width: auto" class="table table-sm table-bordered">
						<tr>
							<th style="width: 150px">출금 계좌</th>
							<td><input id="fromAccount" type="text" name="fromAno" value="1" /></td>
						</tr>
						<tr>
							<th>입금 계좌</th>
							<td><input id="toAccount" type="text" name="toAno" value="2" /></td>
						</tr>
						<tr>
							<th>이체 금액</th>
							<td><input id="amount" type="text" name="amount" value="10000" /></td>
						</tr>
					</table>
					<input class="btn btn-info" type="submit" value="이체하기" />
				</form>
			</div>
		</div>
		<div class="card">
			<div class="card-header">선언적(어노테이션) 방식</div>
			<div class="card-body">
				<form id="accountTransferForm2" method="post" action="transaction2">
				        <table style="width:auto" class="table table-sm table-bordered">
				      <tr>
				         <th style="width:150px">출금 계좌</th>
				         <td><input id="fromAccount" type="text" name="fromAno" value="1"/></td>
				      </tr>
				      <tr>
				         <th>입금 계좌</th>
				         <td><input id="toAccount" type="text" name="toAno" value="2"/></td>
				      </tr>
				      <tr>
				         <th>이체 금액</th>
				         <td><input id="amount" type="text" name="amount" value="10000"/></td>
				      </tr>
				   </table>
				   <input class="btn btn-info" type="submit" value="이체하기"/>                  
				</form>
			</div>
		</div>
	</div>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/common/header.jsp"%>

<div class="card m-2">
   <div class="card-header">
      ExecutorService
   </div>
   <div class="card-body">
      <a href="joinEvent" class="btn btn-info btn-sm">이벤트 참여1</a>
      <a href="javascript:joinEvent2()" class="btn btn-info btn-sm">이벤트 참여2</a>
   </div>
   <script>
      function joinEvent2() {
         setInterval(() => {
            callAjax();
         }, 10);
      }
      
      function callAjax() {
         $.ajax({
            url:"joinEvent2"
         }).done((data) => {
            console.log(data.result);
         });
      }
   </script>
</div>
<%@ include file="/WEB-INF/views/common/footer.jsp"%>
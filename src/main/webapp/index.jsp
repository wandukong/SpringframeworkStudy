<%@ page contentType="text/html; charset=UTF-8"%>

<%
// System.out.println(request.getServletContext().getContextPath());
// System.out.println(application.getContextPath());
// System.out.println(request.getContextPath());

response.sendRedirect(application.getContextPath() + "/ch01/content");
%>


<%@page import="oracle.jdbc.diagnostics.Parameter"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "free.board.ReplyDAO" %>
<%@ page import = "java.sql.Timestamp" %>

<% request.setCharacterEncoding("UTF-8");%>

<jsp:useBean id="dto" scope="page" class="free.board.ReplyDTO">
   <jsp:setProperty name="dto" property="*"/>
</jsp:useBean>
 
<%
   ReplyDAO dao = ReplyDAO.getInstance();   //dao 객체 생성
   dao.insertReply(dto);   //insertArticle 실행
   String pageNum = request.getParameter("pageNum");
   
    response.sendRedirect("content.jsp?num="+dto.getBoardnum()+"&pageNum="+pageNum);   //insert 이후 list로 결과 확인
%>
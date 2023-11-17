<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import = "free.member.MemberDAO" %>
<%@ page import = "java.sql.Timestamp" %>

<% request.setCharacterEncoding("UTF-8");%>

<jsp:useBean id="member" class="free.member.MemberDTO">
    <jsp:setProperty name="member" property="*" />
</jsp:useBean>

<%
    member.setReg_date(new Timestamp(System.currentTimeMillis()) );
	MemberDAO manager = MemberDAO.getInstance();
    manager.insertMember(member);

    response.sendRedirect("loginForm.jsp");
%>
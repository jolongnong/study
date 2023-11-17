<%@page import="java.util.ArrayList"%>
<%@ page contentType = "text/html; charset=UTF-8" %>
<%@ page import = "free.board.BoardDAO" %>
<%@ page import = "free.board.BoardDTO" %>
<%@ page import = "free.board.ReplyDTO" %>
<%@ page import = "free.board.ReplyDAO" %>
<%@ page import = "java.util.List" %>
<%@ page import = "java.text.SimpleDateFormat" %>
<%@ include file="/views/color.jsp"%>
<html>
<head>
<title>게시판</title>
<link href="style.css" rel="stylesheet" type="text/css">
</head>

<%
      int num = Integer.parseInt(request.getParameter("num"));
      String pageNum = request.getParameter("pageNum");
      String id = (String)session.getAttribute("memId");

      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

   BoardDAO dbPro = BoardDAO.getInstance();
   BoardDTO article =  dbPro.getArticle(num);
   int ref=article.getRef();
   int re_step=article.getRe_step();
   int re_level=article.getRe_level();

   String writer = dbPro.getWriter(num);
%>

   <body bgcolor="<%=bodyback_c%>">     
   <center>
   <h3>글내용 보기</h3>
   <%if(re_step == 0 || id.equals("admin1") || id.equals(writer)) { %>
   <form>
   <table width="500" border="1" cellspacing="0" cellpadding="0"  bgcolor="<%=bodyback_c%>" align="center">  
     <tr height="30">
       <td align="center" width="125" bgcolor="<%=value_c%>">글번호</td>
       <td align="center" width="125" align="center">
           <%=article.getNum()%></td>
       <td align="center" width="125" bgcolor="<%=value_c%>">조회수</td>
       <td align="center" width="125" align="center">
           <%=article.getReadcount()%></td>
     </tr>
     <tr height="30">
       <td align="center" width="125" bgcolor="<%=value_c%>">작성자</td>
       <td align="center" width="125" align="center">
           <%=article.getWriter()%></td>
       <td align="center" width="125" bgcolor="<%=value_c%>" >작성일</td>
       <td align="center" width="125" align="center">
           <%= sdf.format(article.getReg_date())%></td>
     </tr>
     <tr height="30">
       <td align="center" width="125" bgcolor="<%=value_c%>">글제목</td>
       <td align="center" width="375" align="center" colspan="3">
           <%=article.getSubject()%></td>
     </tr>
     <tr>
       <td align="center" width="125" bgcolor="<%=value_c%>">글내용</td>
       <td align="left" width="375" colspan="3"><pre><%=article.getContent()%></pre></td>
     </tr>
     <tr height="30">      
       <td colspan="4" bgcolor="<%=value_c%>" align="right" >
       <%
          if(id != null) {
             if(id.equals(article.getWriter())) {%>
               <input type="button" value="글수정" 
                onclick="document.location.href='updateForm.jsp?num=<%=article.getNum()%>&pageNum=<%=pageNum%>'">
               &nbsp;&nbsp;&nbsp;&nbsp;
             <%}
            if(id.equals(article.getWriter()) || id.equals("admin1")) {%>   
               <input type="button" value="글삭제" 
                onclick="document.location.href='deleteForm.jsp?num=<%=article.getNum()%>&pageNum=<%=pageNum%>'">
               &nbsp;&nbsp;&nbsp;&nbsp;
             <%}
            if(id.equals("admin1")) {%>
               <input type="button" value="답글쓰기" 
                  onclick="document.location.href='writeForm.jsp?num=<%=num%>&ref=<%=ref%>&re_step=<%=re_step%>&re_level=<%=re_level%>'">
                &nbsp;&nbsp;&nbsp;&nbsp;
             <%}
         }%>
          <input type="button" value="글목록" 
          onclick="document.location.href='list.jsp?pageNum=<%=pageNum%>'">
          &nbsp;&nbsp;&nbsp;&nbsp;
       </td>
     </tr>
   </table>
   </form>
   <br />
   
   <%
      ReplyDAO dao = ReplyDAO.getInstance();
      List list = dao.getReplyList(1, 10, article.getNum());
      
      if(list != null) {
         for (int i = 0 ; i < list.size() ; i++) {
             ReplyDTO dto = (ReplyDTO)list.get(i);%>
             
             <%=dto.getWriter() %>
             <span> : </span>  
             <%=dto.getContent() %> 
             <hr width="500"/>
                    
      <% 
         }
      }%>
   
   
   <%
      int r_num=0,r_ref=1,r_re_step=0,r_re_level=0;
   %>
   
   <form action="replyPro.jsp" method="post">
      <input type="hidden" name="boardnum" value="<%=article.getNum()%>"/>
      <input type="hidden" name="writer" value="<%=id%>"/>
	  <input type="hidden" name="pageNum" value="<%=pageNum%>"/>
      <input type="hidden" name="num" value="<%=r_num%>"/>
      <input type="hidden" name="ref" value="<%=r_ref%>"/>
      <input type="hidden" name="re_step" value="<%=r_re_step%>"/>
      <input type="hidden" name="re_level" value="<%=r_re_level%>"/> 
      <textarea rows="1.5" cols="68" name="content"></textarea>
      <br />
      <br />
      <input type="submit" value="등록"/>   
   </form>
   
  
       
   <%}else{%>
     <script type="text/javascript">
        alert("작성자만 읽을 수 있습니다!!");
        history.go(-1);
     </script>
   <%}%>
   </center>     
   </body>
   
</html>
   
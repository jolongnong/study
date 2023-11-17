package free.board;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReplyDAO {
   
   private static ReplyDAO dao = new ReplyDAO();
         
   public static ReplyDAO getInstance() {
      return dao;
   }
   private ReplyDAO(){}
   
   private Connection getConnection() throws Exception { 
      Connection conn = null;
      try {
         Class.forName("oracle.jdbc.driver.OracleDriver");      
         String url ="jdbc:oracle:thin:@192.168.219.123:1521/orcl";
         String user="java17";
         String pw ="java";
         conn = DriverManager.getConnection(url,user,pw);  
      }catch(Exception e) {
         e.printStackTrace();
      }
      return conn;
   }
   
public void insertReply(ReplyDTO dto) throws Exception { 
      
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      int num=dto.getNum();
      int ref=dto.getRef();
      int re_step=dto.getRe_step();
      int re_level=dto.getRe_level();
      int number=0;
      String sql="";
      try {
         conn = getConnection(); 
         pstmt = conn.prepareStatement("select max(num) from freereplyboard");
         rs = pstmt.executeQuery();
         if (rs.next()) 
            number=rs.getInt(1)+1;   
         else
            number=1; 
         if (num!=0) 
         { 
            sql="update freereplyboard set re_step=re_step+1 where ref= ? and re_step > ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, ref);
            pstmt.setInt(2, re_step);
            pstmt.executeUpdate();
            re_step=re_step+1;
            re_level=re_level+1;
         }else{ 
            ref=number;
            re_step=0;
            re_level=0;
         }
 
         sql = "insert into freereplyboard values "
         + " (freereplyboard_seq.NEXTVAL,?,?,?,sysdate,?,?,?)";
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, dto.getBoardnum());
         pstmt.setString(2, dto.getWriter());
         pstmt.setString(3, dto.getContent());
         pstmt.setInt(4, ref);
         pstmt.setInt(5, re_step);
         pstmt.setInt(6, re_level);
         pstmt.executeUpdate();
         
      } catch(Exception ex) {
         ex.printStackTrace();
      } finally {
         if (rs != null) try { rs.close(); } catch(SQLException ex) {}
         if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
         if (conn != null) try { conn.close(); } catch(SQLException ex) {}
      }
   }
   public List getReplyList(int start, int end, int boardnum) throws Exception {
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      List articleList=null;
      try {
         conn = getConnection();
         pstmt = conn.prepareStatement(
               "select * from "
               + " (select b.* , rownum r from "
               + " (select * from freereplyboard where boardnum=? order by ref desc , re_step asc) b) "
               + " where r >= ? and r <= ? ");
               pstmt.setInt(1, boardnum); 
               pstmt.setInt(2, start); 
               pstmt.setInt(3, end); 
   
               rs = pstmt.executeQuery(); 
               if (rs.next()) {
                  articleList = new ArrayList(end); 
                  do{ 
                     ReplyDTO dto= new ReplyDTO();
                     dto.setNum(rs.getInt("num"));
                     dto.setBoardnum(rs.getInt("boardnum"));  
                     dto.setWriter(rs.getString("writer"));
                     dto.setReg_date(rs.getTimestamp("reg_date"));
                     dto.setRef(rs.getInt("ref"));
                     dto.setRe_step(rs.getInt("re_step"));
                     dto.setRe_level(rs.getInt("re_level"));
                     dto.setContent(rs.getString("content"));
                     
                     articleList.add(dto); 
                  }while(rs.next());
               }
      } catch(Exception ex) {
         ex.printStackTrace();
      } finally {
         if (rs != null) try { rs.close(); } catch(SQLException ex) {}
         if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
         if (conn != null) try { conn.close(); } catch(SQLException ex) {}
      }  
      return articleList;
   }
}
package free.member;
import java.sql.*;
public class MemberDAO {
   
	private static MemberDAO instance = new MemberDAO();
   
   public static MemberDAO getInstance() {return instance; }
   
   private MemberDAO() {}
   
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

   public void insertMember(MemberDTO member) 
   throws Exception {
       Connection conn = null;
       PreparedStatement pstmt = null;
       
       try {
           conn = getConnection();
           
           pstmt = conn.prepareStatement(
           	"insert into freemember values (?,?,?,?,?,?,?,?)");
           pstmt.setString(1, member.getId());
           pstmt.setString(2, member.getPasswd());
           pstmt.setString(3, member.getName());
           pstmt.setString(4, member.getJumin1());
           pstmt.setString(5, member.getJumin2());
           pstmt.setString(6, member.getEmail());
           pstmt.setString(7, member.getBlog());
			pstmt.setTimestamp(8, member.getReg_date());
           pstmt.executeUpdate();
       } catch(Exception ex) {
           ex.printStackTrace();
       } finally {
           if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
           if (conn != null) try { conn.close(); } catch(SQLException ex) {}
       }
   }

	public int userCheck(String id, String passwd) 
	throws Exception {
		Connection conn = null;
       PreparedStatement pstmt = null;
		ResultSet rs= null;
       String dbpasswd="";
		int x=-1;
       
		try {
           conn = getConnection();
           
           pstmt = conn.prepareStatement(
           	"select passwd from freemember  where id = ?");
           pstmt.setString(1, id);
           rs= pstmt.executeQuery();

			if(rs.next()){
				dbpasswd= rs.getString("passwd"); 
				if(dbpasswd.equals(passwd))
					x= 1; 
				else
					x= 0; 
			}else
				x= -1;
			
       } catch(Exception ex) {
           ex.printStackTrace();
       } finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
           if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
           if (conn != null) try { conn.close(); } catch(SQLException ex) {}
       }
		return x;
	}

	public int confirmId(String id) 
	throws Exception {
		Connection conn = null;
       PreparedStatement pstmt = null;
		ResultSet rs= null;
       String dbpasswd="";
		int x=-1;
       
		try {
           conn = getConnection();
           
           pstmt = conn.prepareStatement(
           	"select id from freemember where id = ?");
           pstmt.setString(1, id);
           rs= pstmt.executeQuery();

			if(rs.next())
				x= 1; //해당 아이디 있음
			else
				x= -1;//해당 아이디 없음		
       } catch(Exception ex) {
           ex.printStackTrace();
       } finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
           if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
           if (conn != null) try { conn.close(); } catch(SQLException ex) {}
       }
		return x;
	}


   public MemberDTO getMember(String id)
   throws Exception {
       Connection conn = null;
       PreparedStatement pstmt = null;
       ResultSet rs = null;
       MemberDTO member=null;
       try {
           conn = getConnection();
           
           pstmt = conn.prepareStatement(
           	"select * from freemember where id = ?");
           pstmt.setString(1, id);
           rs = pstmt.executeQuery();

           if (rs.next()) {
               member = new MemberDTO();
               member.setId(rs.getString("id"));
               member.setPasswd(rs.getString("passwd"));
				member.setName(rs.getString("name"));
               member.setJumin1(rs.getString("jumin1"));
               member.setJumin2(rs.getString("jumin2"));
				member.setEmail(rs.getString("email"));
				member.setBlog(rs.getString("blog"));
               member.setReg_date(rs.getTimestamp("reg_date"));     
			}
       } catch(Exception ex) {
           ex.printStackTrace();
       } finally {
           if (rs != null) try { rs.close(); } catch(SQLException ex) {}
           if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
           if (conn != null) try { conn.close(); } catch(SQLException ex) {}
       }
		return member;
   }
   
   
   
   
   public void updateMember(MemberDTO member)
   throws Exception {
       Connection conn = null;
       PreparedStatement pstmt = null;
       try {
           conn = getConnection();
           
           pstmt = conn.prepareStatement(
             "update freemember set passwd=?,name=?,email=?,blog=? where id=?");
           pstmt.setString(1, member.getPasswd());
           pstmt.setString(2, member.getName());
           pstmt.setString(3, member.getEmail());
           pstmt.setString(4, member.getBlog());
           pstmt.setString(5, member.getId());
           
           pstmt.executeUpdate();
       } catch(Exception ex) {
           ex.printStackTrace();
       } finally {
           if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
           if (conn != null) try { conn.close(); } catch(SQLException ex) {}
       }
   }
   
   public int deleteMember(String id, String passwd)
   throws Exception {
       Connection conn = null;
       PreparedStatement pstmt = null;
       ResultSet rs= null;
       String dbpasswd="";
       int x=-1;
       try {
			conn = getConnection();

           pstmt = conn.prepareStatement(
           	"select passwd from freemember where id = ?");
           pstmt.setString(1, id);
           rs = pstmt.executeQuery();
           
			if(rs.next()){
				dbpasswd= rs.getString("passwd"); 
				if(dbpasswd.equals(passwd)){
					pstmt = conn.prepareStatement("delete from freemember where id=?");
					pstmt.setString(1, id);
					pstmt.executeUpdate();
					x= 1; 
				}else
					x= 0; 
			}
       } catch(Exception ex) {
           ex.printStackTrace();
       } finally {
           if (rs != null) try { rs.close(); } catch(SQLException ex) {}
           if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
           if (conn != null) try { conn.close(); } catch(SQLException ex) {}
       }
		return x;
   }
}

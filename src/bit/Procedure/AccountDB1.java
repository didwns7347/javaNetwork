package bit.Procedure;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;


public class AccountDB1 {
	private Connection con = null;
	
	//생성자
	public AccountDB1()  {		
	}
	
	//DB연결(Connection 객체 생성)
	public boolean Run() {
		try {			
			Class.forName("com.mysql.cj.jdbc.Driver");	//"com.mysql.jdbc.Driver"		
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/exampleDB?serverTimezone=UTC","root","1234");
			con.setAutoCommit(false);	
			System.out.println("데이터 베이스 연결 성공");
			return true;
			
		} catch (Exception e) {
			System.out.println("[데이터베이스 초기화 에러] " + e.getMessage());
			return false;
		}
	}
	
	//기능
	public boolean MakeAccount(int accnum, String name, int balance) {
		try {
			String qury = "{call MakeAccount(?,?,?)};";
			PreparedStatement sment = con.prepareStatement(qury);			
			sment.setInt(1,  accnum);
			sment.setString(2,  name);
			sment.setInt(3,  balance);
			int i = sment.executeUpdate();
			sment.close();   //<===================================
			if( i > 0) {
				con.commit();
				return true;
			}	
			return false;
		}
		catch(Exception ex) {
			return false;
		}
	}

	public Account SelectAccount(int accnum) {
		try {
			String sql = "{call SelectAccount(?,?,?,?)};";
			CallableStatement sment = con.prepareCall(sql);	
			sment.setInt(1, accnum);
			sment.registerOutParameter(2, Types.VARCHAR);
			sment.registerOutParameter(3,  Types.INTEGER);
			sment.registerOutParameter(4,  Types.TIMESTAMP);
			//--------------------------------------------------------
			sment.execute();			
	
			String name = sment.getString(2);
			int balance = sment.getInt(3);
			//[날짜 수정] (1) 아래 코드 수정			
			Timestamp ntime = sment.getTimestamp(4, Calendar.getInstance());
			/*
			 * (2) Account 클래스 맴버 함수 수정
			 * newtime.get(Calendar.MONTH)+1
			 * public String GetDate() {
				String temp = String.format("%04d-%02d-%02d", 
				newtime.get(Calendar.YEAR) ,newtime.get(Calendar.MONTH)+1, newtime.get(Calendar.DAY_OF_MONTH));
				return temp;
				}
			 */			
			sment.close();
			
			Account acc = new Account(accnum, name, balance, ntime);
			return acc;
		}
		catch(Exception ex) {
			System.out.println("예외 발생");
			return null;
		}		
	}

	public boolean InputAccount(int accnum, int balance) {
		try {
			String sql = "{call InputAccount(?,?)};";
			CallableStatement sment = con.prepareCall(sql);			
			sment.setInt(1,  accnum);
			sment.setInt(2,  balance);
			sment.execute();
			sment.close();   //<===================================			
			return true;
		}
		catch(Exception ex) {
			return false;
		}
	}
	
	public boolean OutputAccount(int accnum, int balance) {
		try {
			String sql = "{call OutputAccount(?,?)};";
			CallableStatement sment = con.prepareCall(sql);			
			sment.setInt(1,  accnum);
			sment.setInt(2,  balance);
			sment.execute();
			sment.close();   //<===================================			
			return true;
		}
		catch(Exception ex) {
			return false;
		}
	}
	
	public boolean DeleteAccount(int accnum) {		//3
		try {
			String sql = "{call DeleteAccount(?)};";
			CallableStatement sment = con.prepareCall(sql);			
			sment.setInt(1,  60);
			sment.execute();
			sment.close();   //<===================================			
			return true;
		}
		catch(Exception ex) {
			System.out.println("삭제 오류"+ex.getMessage());
			return false;
		}
	}

	public ArrayList<Account> SelectAllAccount(){
		try {
			String sql = "select * from account;";
			PreparedStatement sment = con.prepareStatement(sql);			
			//--------------------------------------------------------
			ArrayList<Account> acclist= new ArrayList<Account>();  //<----------
			ResultSet rs = sment.executeQuery();
			while(rs.next()) {
				int accid = rs.getInt(1);
				String name = rs.getString(2);
				int balance = rs.getInt(3);
				Timestamp ntime = rs.getTimestamp(4);
				acclist.add(new Account(accid, name, balance, ntime)); //<-------
			}
			sment.close();			
			
			return acclist;
		}
		catch(Exception ex) {
			return null;
		}		
	}
}
















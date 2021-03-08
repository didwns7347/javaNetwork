package bit.Server.pro;

import java.sql.Timestamp;
import java.util.Calendar;

public class Start {
	
	public static void main(String[] args) {
		//TcpIpMultiServer server =new TcpIpMultiServer();
		//server.Run();
		//Account acc = new Account()
		Manager.getInstance().Run();
		/*try {
			AccountDB1 db = new AccountDB1();
		}catch(Exception e) {
			
		}*/
		
	}
}
/* 121.138.83.110
서브넷 마스크 . . . . . . . : 255.255.255.128
기본 게이트웨이 . . . . . . : 121.138.83.126*/
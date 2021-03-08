package bit.Server;

import java.util.ArrayList;
import java.util.HashMap;

public class Manager {
	//싱글톤 패턴 코드 -------------------------------------------------
		//생성자 은닉!
		private Manager() {}
		
		//자신의 static 객체 생성
		private static Manager Singleton = new Manager();
		private TcpIpMultiServer server = new TcpIpMultiServer();
		
		private AccountDB1 db= new AccountDB1();
		public void Run() {
			if(db.Run()==false)
				System.out.println("server exit");
				System.exit(0);//finish
			server.Run();
			
		}
		//계좌번호 계좌
		//내부적으로 생성된 자신의 객체를 외부에 노출 메서드
		public static Manager getInstance() {
			return Singleton;
		}
		private Parser parser = new Parser();
		public String RecvData(String msg) {
			return Parser.RecvData(msg);
		}
		
		//파서에서 분석된 결과에 따라 해당 함수를 호출해줌
		public String MakeAccount(int id, String name, int balance) {
			//저장!
			String msg = null;
			
			Account acc = new Account(id, name, balance);
			System.out.println("[수신메시지]");		//<============ test코드------
			acc.Print();							//<============ test코드------
			if( db.MakeAccount(id, name, balance) == true)
				msg = Packet.MakeAccount_ack(id, true);
			else
				msg = Packet.MakeAccount_ack(id, false);
			
			//클라이언트에 전송!
			return msg;
		}
		public String InputAccount(int id, int money) {
			String msg=null;
			// TODO Auto-generated method stub
			if(db.InputAccount(id,money)) {
				msg = Packet.InputAccount_ack(id,money, true);
			}
			else {
				msg = Packet.InputAccount_ack(id,money ,false);
			}
			return msg;
		}
		public String OutputAccount(int id, int money) {
			// TODO Auto-generated method stub
			String msg=null;
			if(db.OutputAccount(id,money)) {
				msg = Packet.OutputAccount_ack(id,money, true);
			}
			else
				msg = Packet.OutputAccount_ack(id,money ,false);
			return msg;
		}

		
		public String SelectAccount(int id) {
			Account acc=db.SelectAccount(id);
			String msg;
			//패킷 생성
			if(acc==null) {
				msg=Packet.SelectAccount_ack(id, "-", 0, "","",false);
			}
			else {
				msg=Packet.SelectAccount_ack(acc.getAccid(), acc.getName(), acc.getBalance(),acc.GetDate(),acc.GetTime() ,true);
			}
			return msg;
		}
		public String DeleteAccount(int id) {
			// TODO Auto-generated method stub
			String msg;
			if( db.DeleteAccount(id) == true)
				msg = Packet.DeleteAccount_ack(id, true);
			else
				msg = Packet.DeleteAccount_ack(id, false);
			
			return msg;
		}
		public String SelectAllAccount() {
			// TODO Auto-generated method stub
			String msg="";
			ArrayList<Account> arr =db.SelectAllAccount();
			if(arr!=null) {
				msg=Packet.SelectAllAccount(arr, true);
			}
			else {
				msg=Packet.SelectAllAccount(arr, false);
			}
			return msg;
		}
}

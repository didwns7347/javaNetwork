//bank.java
package bit.Client;

import java.util.ArrayList;

public class Bank {
	//통신 모듈
	private TcpIpMultiClient2 client;	
	//Bank 생성시 서버 접속 요청!
	public Bank() {
		client =  new TcpIpMultiClient2(this);	
		client.Run();
		
	}
	//메서드
	private int InputMax() {
		return BitGlobal.InputNumber("저장 개수");
	}
	
	public void MakeAccount() {
		try {
			int number = BitGlobal.InputNumber("계좌번호");
			String name = BitGlobal.InputString("이름");
			int money = BitGlobal.InputNumber("입금액");
					
			String msg=Packet.MakeAccount(number, name, money);
			//System.out.println(msg+"check point 1");
			client.SendMessage(msg);
			System.out.println("서버로 신규 계좌저오 전송");
		}
		catch(Exception ex) {
			System.out.println("[전송실패] " + ex.getMessage());
		}
	}
		
	public void SelectAccount() {
		try {
			int number = BitGlobal.InputNumber("계좌번호");
			String msg=Packet.SelectAccount(number);
			client.SendMessage(msg);
			System.out.println("서버로 검색할 계좌 번호를 전송");
		}
		catch(Exception ex) {
			System.out.println("[전송실패] " + ex.getMessage());
		}
	}
		
	public void InputMoney() {
		try {
			int number = BitGlobal.InputNumber("계좌번호");
			int money = BitGlobal.InputNumber("입금액");
					
			String msg=Packet.InputAccount(number, money);
			client.SendMessage(msg);
			System.out.println("서버로 계좌입금 정보를 전송");
		}
		catch(Exception ex) {
			System.out.println("[전송실패] " + ex.getMessage());
		}
	}
		
	public void OutputMoney() {
		try {
			int number = BitGlobal.InputNumber("계좌번호");
			int money = BitGlobal.InputNumber("출금액");
					
			String msg=Packet.OutputAccount(number, money);
			client.SendMessage(msg);
			System.out.println("서버로 계좌출금 정보를 전송");
		}
		catch(Exception ex) {
			System.out.println("[전송실패] " + ex.getMessage());
		}
	}
	
	public void DeleteAccount() {		
		try {
			int number = BitGlobal.InputNumber("계좌번호");
			String msg=Packet.DeleteAccount(number);
			client.SendMessage(msg);
			System.out.println("서버로 계좌삭제 정보를 전송");
		}
		catch(Exception ex) {
			System.out.println("[전송실패] " + ex.getMessage());
		}
	}
	public void PrintAll() {
		try {	
			String msg=Packet.SelectAllAccount();
			client.SendMessage(msg);
			System.out.println("서버로 전체계좌 리스트 정보요청");
		}
		catch(Exception ex) {
			System.out.println("[전송실패] " + ex.getMessage());
		}
	}
	//-----------------------------통신모듈에서 수신된 데이터를 받는 기능
	public void RecvData(String msg) {
		Parser.RecvData(msg,this);
	}
	public void MakeAccount_Ack(int number, String result) {
		System.out.println();
		if(result.equals("S"))
			System.out.println(number + "계좌 생성 성공");
		else
			System.out.println(number + "계좌 생성 실패");
	}
	public void SelectAccount_Ack(String result, int number, String name, int balance,String date,String time) {
		if(result.equals("F")) {
			System.out.println(number + "계좌 번호는 없는 번호입니다.");
			return;
		}
		System.out.println();
		System.out.println("계좌번호 : " + number);
		System.out.println("이름 : " + name);
		System.out.println("잔액 : " + balance);
		System.out.println("일자: "+date+"\n시간: 222222222222222222222222222222"+time);
		

	}
	public void InputAccount_Ack(String result, int number, int balance) {
		// TODO Auto-generated method stub
		if(result.equals("F")) {
			System.out.println(number + "계좌 번호는 없는 번호입니다.");
			return;
		}
		System.out.println();
		System.out.println("계좌번호 : " + number);
		System.out.println("입금액 : " + balance);
		
	}
	public void OutputAccount_Ack(String result, int number, int balance) {
		// TODO Auto-generated method stub
		if(result.equals("F")) {
			System.out.println(number + "계좌 번호는 없는 번호입니다.");
			return;
		}
		System.out.println();
		System.out.println("계좌번호 : " + number);
		System.out.println("출금액 : " + balance);
		
	}
	public void DeleteAccount_Ack(String result, int number) {
		// TODO Auto-generated method stub
		System.out.println();
		if(result.equals("F")) {
			System.out.println(number + "계좌 번호는 없는 번호입니다.");
			return;
		}
		System.out.println();
		System.out.println("계좌번호 : " + number+"가 삭제 되었습니다.");
		
	}
	public void SelectAllAccount_Ack(String result, ArrayList<Account> acclist) {
		// TODO Auto-generated method stub
		System.out.println("\n저장 개수"+ acclist.size());
		for(Account acc:acclist) {
			acc.Print();
		}
		
	}
}










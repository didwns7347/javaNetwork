package bit.Server.pro;

import java.util.ArrayList;

/*
[client -> Server]
   "MakeAccount@111#ccm#1000"
   "SelectAccount@111"
   "InputAccount@계좌번호#입금액
   "OutputAccount@계좌번호#입금액
   "DeleteAccount@1111
   "SelectAllAccount@
[server -> Client]
	"MakeAccount_ck@111#S"
	"MakeAccount_ack@111#F"
	success -> SelectAccount_ack@S#id#name#balance
	fail-> SelectAccount_ack@F#id
*/
public class Packet {
	public static String MakeAccount_ack(int id,boolean b) {
		String msg="";
		msg+="MakeAccount"+"@";
		msg+=id;
		if(b)
			msg+="S";
		else
			msg+="F";
		
		return msg;
	}
	
	
	public static String SelectAccount_ack(int id, String name, int balance,String date,String time,boolean b ) {
		String msg="";
		msg+="SelectAccount_ack"+"@";
		if(b)
			msg+="S#";
		else
			msg+="F#";
		
		msg+=id+"#";
		msg+=name+"#";
		msg+=balance+"#";
		msg+=date+"#";
		msg+=time;
		return msg;
	}


	public static String InputAccount_ack(int id, int money, boolean b) {
		String msg="";
		msg+="InputAccount_ack"+"@";
		if(b)
			msg+="S#";
		else
			msg+="F#";
		
		msg+=id+"#"+money;
		return msg;
		// TODO Auto-generated method stub
		
	}


	public static String OutputAccount_ack(int id, int money, boolean b) {
		String msg="";
		msg+="OutputAccount_ack"+"@";
		if(b)
			msg+="S#";
		else
			msg+="F#";
		
		msg+=id+"#"+money;
		return msg;
		// TODO Auto-generated method stub
	}


	public static String DeleteAccount_ack(int id, boolean b) {
		// TODO Auto-generated method stub
		String msg="";
		msg+="DeleteAccount_ack"+"@";
		if(b)
			msg+="S#";
		else
			msg+="F#";
		
		msg+=id;
		return msg;
	}

	//header(flag)@ S or F#account1%name%id%balance#_________________________
	
	public static String SelectAllAccount(ArrayList<Account> arr, boolean b) {
		// TODO Auto-generated method stub
		String msg="SelectAllAccount_ack@";
		if(b) msg+="S#";
		else msg+="F#";
		for(Account ac:arr) {
			msg+=ac.getAccid()+"%";
			msg+=ac.getName()+"%";
			msg+=ac.getBalance()+"%";
			msg+=ac.GetDate()+"%";
			msg+=ac.GetTime()+"#";
		}
		return null;
	}
	
}

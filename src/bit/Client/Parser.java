package bit.Client;

import java.util.ArrayList;

/*
[server -> Client]
	"MakeAccount_ack@111#S"   			"MakeAccount_ack@111#F"
	"SelectAccount_ack@S#111#ccm#1000#날짜#시간"	"SelectAccount_ack@F#111#-#0#-#-"
	"InputAccount_ack@S#111#입금액"				"SelectAccount_ack@F#111#입금액"
	"OutputAccount_ack@S#111#출금액"				"SelectAccount_ack@F#111#출금액"
*/
public class Parser {	
	public static void RecvData(String msg, Bank bank) {
		String[] filter = msg.split("@");
		if( filter[0].equals("MakeAccount_ack")) {
			String[] filter1 = filter[1].split("#");
			int number    = Integer.parseInt(filter1[0]);
			String result = filter1[1];
			
			bank.MakeAccount_Ack(number, result);			
		}
		else if( filter[0].equals("SelectAccount_ack")) {
			String[] filter1 = filter[1].split("#");
			String result 	= filter1[0];
			int number    	= Integer.parseInt(filter1[1]);
			String name 	= filter1[2];
			int balance 	= Integer.parseInt(filter1[3]);
			String date 	= filter1[4];
			String time		= filter1[5];
			
			bank.SelectAccount_Ack(result, number, name, balance, date, time);			
		}
		else if( filter[0].equals("InputAccount_ack")) {
			String[] filter1 = filter[1].split("#");
			String result 	= filter1[0];
			int number    	= Integer.parseInt(filter1[1]);
			int balance 	= Integer.parseInt(filter1[2]);
			
			bank.InputAccount_Ack(result, number, balance);			
		}
		else if( filter[0].equals("OutputAccount_ack")) {
			String[] filter1 = filter[1].split("#");
			String result 	= filter1[0];
			int number    	= Integer.parseInt(filter1[1]);
			int balance 	= Integer.parseInt(filter1[2]);
			
			bank.OutputAccount_Ack(result, number, balance);			
		}
		else if( filter[0].equals("DeleteAccount_ack")) {
			String[] filter1 = filter[1].split("#");
			String result 	= filter1[0];
			int number=Integer.parseInt(filter1[1]);
			
			
			bank.DeleteAccount_Ack(result, number);		
		}
		
		else if( filter[0].equals("SelectAllAccount_ack")) {
			String[] filter1 = filter[1].split("#");
			String result 	= filter1[0];
			
			ArrayList<Account> acclist = new ArrayList<Account>();
			for(int i=1; i< filter1.length; i++) {
				String[] filter2 = filter1[i].split("%");
				int number = Integer.parseInt(filter2[0]);
				String name = filter2[1];
				int balance = Integer.parseInt(filter2[2]);
				String date = filter2[3];
				String time = filter2[4];
				acclist.add(new Account(number, name, balance, date, time));
			}
			bank.SelectAllAccount_Ack(result, acclist);
		}
	}
}






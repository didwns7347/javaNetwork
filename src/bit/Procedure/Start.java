package bit.Procedure;

public class Start {
	public static void exam1() {
		AccountDB1 db = new AccountDB1();
		db.Run();
		
		//db.MakeAccount(60, "ccm1", 2000);
		//Account acc = db.SelectAccount(1234);
		//acc.Println();
		db.DeleteAccount(60);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		exam1();
	}

}

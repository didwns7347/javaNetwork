package bit.Client;

import bit.Server.TcpIpMultiServer;

public class Start {
	public static void main(String[] args) {
		App app = App.getInstance();
		app.Run();
		app.Exit();
	}
}

package bit.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Scanner;

public class TcpIpMultiClient {
	final int PORT = 4000;
	final String SERVER_IP = "14.32.18.42";
	final String name = "kkkkk";

	public void Run() {
		try {
			// create socket and vinding
			Socket socket = new Socket(SERVER_IP, PORT);
			System.out.println("서버 연결 됨");

			Thread sender = new ClientSender(socket, name);
			Thread receiver = new ClientReceiver(socket);

			sender.start();
			receiver.start();
		} catch (ConnectException e) {
			e.printStackTrace();
		} catch (Exception e) {
		}

	}
}

class ClientSender extends Thread {
	private Socket socket;
	private PrintWriter writer;
	private String name;

	public ClientSender(Socket socket, String name) {
		this.socket = socket;
		this.name = name;
		try {
			writer = new PrintWriter(socket.getOutputStream());
		} catch (Exception e) {
		}
	}

	@Override
	public void run() {
		Scanner kb = new Scanner(System.in);
		if (writer != null) {
			writer.println(name);
			writer.flush();
		}
		// 7. 인풋, 아웃풋 통신
		// 8. 연결 끊어질때까지 통신
		while (writer != null) {
			writer.println("[" + name + "]" + kb.nextLine());
			writer.flush();
		}
		kb.close();
		// 9. 닫음
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class ClientReceiver extends Thread {
	private Socket socket;
	private BufferedReader reader;

	public ClientReceiver(Socket socket) {
		this.socket = socket;
		try {
			// 6. 소켓으로부터 인풋, 아웃풋 스크림 얻음
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

		} catch (IOException e) {
		}
	}
	@Override
	public void run() {
		// 7.인풋,아웃풋 이용한 통신
		// 8. 연결이 끊어질때까지 통신
		while (reader != null) {
			try {
				String msg =reader.readLine();
				System.out.println(msg);
			} catch (IOException e) {
			}
		}
		try { // 소켓 close()
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}




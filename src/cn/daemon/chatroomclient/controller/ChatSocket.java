package cn.daemon.chatroomclient.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import cn.daemon.chatroomclient.view.ChatWindow;

public class ChatSocket extends Thread {

	Socket socket;
	String username;

	ClientManager clientManager = ClientManager.getClientManager();
	ChatWindow chatWindow;

	public ChatSocket(Socket s, String username) {
		this.socket = s;
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	BufferedReader reader;
	PrintWriter writer;

	@Override
	public void run() {
		ChatWindow chatWindow = clientManager.getChatWindow();
		try {
			writer = new PrintWriter(new OutputStreamWriter(
					socket.getOutputStream()));
			reader = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.startsWith("#UserList#")) {
					String[] userList = line.split("####");
					userList[0] = username;
					chatWindow.initialList(userList);
					continue;
				}
				if (line.startsWith("#AddUser#")) {
					String[] userList = line.split("####");
					String newUser = userList[1];
					chatWindow.addUserToList(newUser);
					continue;
				}
				if (line.startsWith("#DeleteUser#")) {
					String[] userList = line.split("####");
					String newUser = userList[1];
					chatWindow.deleteUserFromList(newUser);
					continue;
				}
				chatWindow.appendMessage(line);
			}
			writer.close();
			reader.close();
			writer = null;
			reader = null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 验证用户名是否存在
	 * 
	 * @return
	 */
	public String checkUsername(String username) {
		try {
			// 输出流
			writer = new PrintWriter(new OutputStreamWriter(
					socket.getOutputStream()));

			// 输入流
			reader = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));

			// 服务器写到客户
			writer.println(username);
			writer.flush();

			String code = reader.readLine();

			return code;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void send(String out) {
		if (writer != null) {
			writer.write(out + "\n");
			writer.flush();
		} else {
			System.out.println("连接中断");
			chatWindow.appendMessage("当前的链接已经中断");
		}
	}

}

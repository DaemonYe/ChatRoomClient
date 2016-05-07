package cn.daemon.chatroomclient.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import cn.daemon.chatroomclient.view.ClientWindow;
import cn.daemon.chatroomclient.view.LoginWindow;

/**
 * 单例模式
 * 
 * @author Demon
 * 
 */
public class ClientManager {
	private ClientManager() {
	}

	private static final ClientManager clientManager = new ClientManager();

	public static ClientManager getClientManager() {
		return clientManager;
	}

	LoginWindow loginWindow;

	public void setLoginWindow(LoginWindow loginWindow) {
		this.loginWindow = loginWindow;
	}

	public LoginWindow getLoginWindow() {
		return loginWindow;
	}

	ClientWindow clientWindow;

	public void setClientWindow(ClientWindow clientWindow) {
		this.clientWindow = clientWindow;
	}

	public ClientWindow getClientWindow() {
		return clientWindow;
	}

	Socket socket;
	BufferedReader reader;
	PrintWriter writer;

	ChatSocket chatSocket;

	public ChatSocket getChatSocket() {
		return chatSocket;
	}

	public void login(final String ip, final String username) {

		new Thread() {
			public void run() {
				try {
					socket = new Socket(ip, 8888);
					// 创建一个新的线程
					chatSocket = new ChatSocket(socket, username);
					// 验证用户名是否存在
					String code = chatSocket.checkUsername(username);

					if (code != null && code.equals("OK")) {
						JOptionPane.showMessageDialog(null, username
								+ "欢迎登录Daemon聊天室！！！");
						ClientWindow clientWindow = new ClientWindow();
						clientWindow.setVisible(true);
						setClientWindow(clientWindow);
						clientManager.getLoginWindow().dispose();
						chatSocket.start();
					} else {
						JOptionPane.showMessageDialog(null, "用户名已占用！！！");
					}

				} catch (UnknownHostException e) {
					JOptionPane.showMessageDialog(null, "服务器未知错误！！！");
					e.printStackTrace();
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "IP地址错误！！！");
					e.printStackTrace();
				}
			};
		}.start();
	}

}

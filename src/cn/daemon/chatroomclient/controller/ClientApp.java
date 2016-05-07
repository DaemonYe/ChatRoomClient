package cn.daemon.chatroomclient.controller;

import java.awt.EventQueue;

import cn.daemon.chatroomclient.view.LoginWindow;

public class ClientApp {

	/**
	 * Launch the client application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginWindow frame = new LoginWindow();
					frame.setVisible(true);
					ClientManager.getClientManager().setLoginWindow(frame);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}

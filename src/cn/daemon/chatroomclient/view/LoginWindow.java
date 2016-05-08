package cn.daemon.chatroomclient.view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import cn.daemon.chatroomclient.controller.ClientManager;

public class LoginWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTextField textIp;
	private JTextField textUsername;
	private JButton button;

	/**
	 * Create the frame.
	 */
	public LoginWindow() {
		setTitle("登录");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 287, 168);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel lblip = new JLabel("\u670D\u52A1\u5668ip");

		textIp = new JTextField();
		textIp.setText("127.0.0.1");
		textIp.setColumns(10);

		JLabel lblogin = new JLabel("\u767B\u5F55\u540D");

		textUsername = new JTextField();
		textUsername.setColumns(10);

		button = new JButton("\u767B\u5F55");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String ip = textIp.getText();
				String username = textUsername.getText();
				if (ip == null || ip.equals(""))
					JOptionPane.showMessageDialog(null, "ip不能为空");
				if (username == null || username.equals(""))
					JOptionPane.showMessageDialog(null, "用户名不能为空");

				ClientManager.getClientManager().login(ip, username);
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane
				.setHorizontalGroup(gl_contentPane
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_contentPane
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																lblip,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addComponent(
																lblogin,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																gl_contentPane
																		.createSequentialGroup()
																		.addComponent(
																				button,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE)
																		.addGap(109))
														.addComponent(
																textUsername,
																Alignment.TRAILING,
																GroupLayout.DEFAULT_SIZE,
																143,
																Short.MAX_VALUE)
														.addComponent(
																textIp,
																Alignment.TRAILING,
																GroupLayout.DEFAULT_SIZE,
																159,
																Short.MAX_VALUE))
										.addContainerGap()));
		gl_contentPane
				.setVerticalGroup(gl_contentPane
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_contentPane
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																textIp,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(lblip))
										.addGap(18)
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																textUsername,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(lblogin))
										.addGap(18).addComponent(button)
										.addContainerGap(22, Short.MAX_VALUE)));
		contentPane.setLayout(gl_contentPane);
	}

	private String getIp() {
		return textIp.getText();
	}
}

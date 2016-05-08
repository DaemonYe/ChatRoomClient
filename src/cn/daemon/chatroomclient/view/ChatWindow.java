package cn.daemon.chatroomclient.view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import cn.daemon.chatroomclient.controller.ClientManager;

public class ChatWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTextArea sendMessage;
	private JTextArea textArea;
	private DefaultListModel model;
	private JList list;
	private JCheckBox isPrivateChat;
	// private List<String> selectedLists;
	private Vector<String> selectedList = new Vector<String>();
	private String message = "";

	/**
	 * Create the frame.
	 * 
	 * @param username
	 */
	public ChatWindow(String title) {
		setTitle(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 545, 353);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		// UserList
		model = new DefaultListModel();

		// 私聊？
		isPrivateChat = new JCheckBox("\u79C1\u804A");

		// 发送按钮
		JButton button = new JButton("\u53D1\u9001");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (isPrivateChat.isSelected() && selectedList.size() >= 1) {
					// 私聊
					String[] allMessage = sendMessage.getText().split(":");

					for (int i = 1; i < allMessage.length; i++) {
						message += allMessage[i];
					}
					if (message.equals("") || message == null) {
						JOptionPane.showMessageDialog(null, "消息不能为空！！！");
						return;
					}
					ClientManager.getClientManager().getChatSocket()
							.send("#PrivateChat#" + sendMessage.getText());
					appendMessage("我  对 " + allMessage[0] + "  说：" + message);
				} else {
					// 公聊
					if (sendMessage.getText().equals("")
							|| sendMessage.getText() == null) {
						JOptionPane.showMessageDialog(null, "消息不能为空！！！");
						return;
					}
					ClientManager.getClientManager().getChatSocket()
							.send(sendMessage.getText());
					appendMessage("我  对  所有人  说：" + sendMessage.getText());
				}
				sendMessage.setText("");
				selectedList.clear();
			}
		});

		JScrollPane userListPane = new JScrollPane();

		JScrollPane messagePane = new JScrollPane();

		JScrollPane sendPane = new JScrollPane();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane
				.setHorizontalGroup(gl_contentPane
						.createParallelGroup(Alignment.TRAILING)
						.addGroup(
								gl_contentPane
										.createSequentialGroup()
										.addComponent(userListPane,
												GroupLayout.DEFAULT_SIZE, 118,
												Short.MAX_VALUE)
										.addGap(6)
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																sendPane,
																GroupLayout.DEFAULT_SIZE,
																385,
																Short.MAX_VALUE)
														.addComponent(
																messagePane,
																Alignment.TRAILING,
																GroupLayout.DEFAULT_SIZE,
																385,
																Short.MAX_VALUE)
														.addGroup(
																Alignment.TRAILING,
																gl_contentPane
																		.createSequentialGroup()
																		.addComponent(
																				isPrivateChat)
																		.addPreferredGap(
																				ComponentPlacement.RELATED,
																				260,
																				Short.MAX_VALUE)
																		.addComponent(
																				button,
																				GroupLayout.PREFERRED_SIZE,
																				76,
																				GroupLayout.PREFERRED_SIZE)))
										.addContainerGap()));
		gl_contentPane
				.setVerticalGroup(gl_contentPane
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_contentPane
										.createSequentialGroup()
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.BASELINE)
														.addGroup(
																gl_contentPane
																		.createSequentialGroup()
																		.addComponent(
																				messagePane,
																				GroupLayout.DEFAULT_SIZE,
																				184,
																				Short.MAX_VALUE)
																		.addGap(8)
																		.addComponent(
																				sendPane,
																				GroupLayout.PREFERRED_SIZE,
																				85,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(4)
																		.addGroup(
																				gl_contentPane
																						.createParallelGroup(
																								Alignment.BASELINE)
																						.addComponent(
																								button)
																						.addComponent(
																								isPrivateChat)))
														.addComponent(
																userListPane,
																GroupLayout.DEFAULT_SIZE,
																304,
																Short.MAX_VALUE))
										.addGap(1)));

		// sendMsg
		sendMessage = new JTextArea();
		sendPane.setViewportView(sendMessage);
		// JScrollPane scrollPane = new JScrollPane(list);

		// AllMsg
		textArea = new JTextArea();
		messagePane.setViewportView(textArea);
		list = new JList(model);
		userListPane.setViewportView(list);

		JLabel userLabel = new JLabel("\u5728\u7EBF\u7528\u6237");
		userListPane.setColumnHeaderView(userLabel);
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String newSelected = (String) list.getSelectedValue();
				if (selectedList != null && selectedList.contains(newSelected))
					selectedList.remove(newSelected);
				else
					selectedList.add(newSelected);
				sendMessage.setText("");
				for (String selected : selectedList) {
					sendMessage.append("@" + selected);
				}
				if (selectedList.size() >= 1)
					sendMessage.append(":");
			}
		});
		contentPane.setLayout(gl_contentPane);
	}

	public void appendMessage(String in) {
		textArea.append("\n" + in);
	}

	/**
	 * 初始化UserList
	 * 
	 * @param userList
	 */
	public void initialList(String[] userList) {
		for (String username : userList) {
			model.addElement(username);
		}
	}

	/**
	 * 新用户，更改UserList
	 * 
	 * @param newUser
	 */
	public void addUserToList(String newUser) {
		model.addElement(newUser);
	}

	/**
	 * 有用户退出，更新UserList
	 * 
	 * @param newUser
	 */
	public void deleteUserFromList(String newUser) {
		model.removeElement(newUser);
	}
}

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
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import cn.daemon.chatroomclient.controller.ClientManager;

public class ClientWindow extends JFrame {

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
	 */
	public ClientWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 545, 353);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		// UserList
		model = new DefaultListModel();
		list = new JList(model);
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
		// JScrollPane scrollPane = new JScrollPane(list);

		// AllMsg
		textArea = new JTextArea();

		// sendMsg
		sendMessage = new JTextArea();

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
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane
				.setHorizontalGroup(gl_contentPane
						.createParallelGroup(Alignment.TRAILING)
						.addGroup(
								gl_contentPane
										.createSequentialGroup()
										.addComponent(list,
												GroupLayout.DEFAULT_SIZE, 144,
												Short.MAX_VALUE)
										.addGap(18)
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																textArea,
																GroupLayout.DEFAULT_SIZE,
																340,
																Short.MAX_VALUE)
														.addGroup(
																gl_contentPane
																		.createSequentialGroup()
																		.addComponent(
																				isPrivateChat)
																		.addPreferredGap(
																				ComponentPlacement.RELATED,
																				215,
																				Short.MAX_VALUE)
																		.addComponent(
																				button,
																				GroupLayout.PREFERRED_SIZE,
																				76,
																				GroupLayout.PREFERRED_SIZE))
														.addComponent(
																sendMessage,
																GroupLayout.DEFAULT_SIZE,
																340,
																Short.MAX_VALUE))
										.addContainerGap()));
		gl_contentPane.setVerticalGroup(gl_contentPane
				.createParallelGroup(Alignment.TRAILING)
				.addGroup(
						gl_contentPane
								.createSequentialGroup()
								.addComponent(textArea,
										GroupLayout.DEFAULT_SIZE, 204,
										Short.MAX_VALUE)
								.addGap(18)
								.addComponent(sendMessage,
										GroupLayout.DEFAULT_SIZE, 84,
										Short.MAX_VALUE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(
										gl_contentPane
												.createParallelGroup(
														Alignment.BASELINE)
												.addComponent(isPrivateChat)
												.addComponent(button)))
				.addComponent(list, GroupLayout.DEFAULT_SIZE, 335,
						Short.MAX_VALUE));
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

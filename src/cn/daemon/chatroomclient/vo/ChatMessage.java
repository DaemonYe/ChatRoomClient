package cn.daemon.chatroomclient.vo;

import java.io.Serializable;

public class ChatMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String username;

	private String message;

	private String msgType;

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ChatMessage(String username, String message, String msgType) {
		super();
		this.username = username;
		this.message = message;
		this.msgType = msgType;
	}

	public ChatMessage() {
		super();
	}

}

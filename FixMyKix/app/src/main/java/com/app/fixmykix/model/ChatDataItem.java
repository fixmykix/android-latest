package com.app.fixmykix.model;

import com.google.gson.annotations.SerializedName;

public class ChatDataItem {

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("receiver_id")
	private int receiverId;

	@SerializedName("conversation_id")
	private int conversationId;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private int id;

	@SerializedName("text")
	private String text;

	@SerializedName("time")
	private String time;

	@SerializedName("ip_address")
	private IpAddress ipAddress;

	@SerializedName("sender_id")
	private int senderId;

	public String getUpdatedAt(){
		return updatedAt;
	}

	public int getReceiverId(){
		return receiverId;
	}

	public int getConversationId(){
		return conversationId;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public int getId(){
		return id;
	}

	public String getText(){
		return text;
	}

	public String getTime(){
		return time;
	}

	public IpAddress getIpAddress(){
		return ipAddress;
	}

	public int getSenderId(){
		return senderId;
	}

	@Override
 	public String toString(){
		return 
			"DataItem{" + 
			"updated_at = '" + updatedAt + '\'' + 
			",receiver_id = '" + receiverId + '\'' + 
			",conversation_id = '" + conversationId + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",id = '" + id + '\'' + 
			",text = '" + text + '\'' + 
			",time = '" + time + '\'' + 
			",ip_address = '" + ipAddress + '\'' + 
			",sender_id = '" + senderId + '\'' + 
			"}";
		}

	public void setText(String text) {
		this.text = text;
	}

	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}
}
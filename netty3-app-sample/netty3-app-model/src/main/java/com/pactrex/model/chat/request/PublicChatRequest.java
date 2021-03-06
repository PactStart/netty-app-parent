package com.pactrex.model.chat.request;

import com.pactrex.common.serial.Serializer;

/**
 * 广播消息
 * @author Di.Lei
 * @date 2017年5月11日
 */
public class PublicChatRequest extends Serializer{
	
	/**
	 * 内容
	 */
	private String context;

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	@Override
	protected void read() {
		this.context = readString();
	}

	@Override
	protected void write() {
		writeString(context);
	}
}

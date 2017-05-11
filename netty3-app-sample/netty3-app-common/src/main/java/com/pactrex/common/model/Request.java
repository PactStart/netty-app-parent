package com.pactrex.common.model;

/**
 * 请求
 * @author Di.Lei
 * @date 2017年5月11日
 */
public class Request {

	private short module;
	
	private short cmd;
	
	private byte[] data;
	
	public static Request valueOf(short module,short cmd,byte[] data){
		Request request = new Request();
		request.setModule(module);
		request.setCmd(cmd);
		request.setData(data);
		return request;
	}

	public short getModule() {
		return module;
	}

	public void setModule(short module) {
		this.module = module;
	}

	public short getCmd() {
		return cmd;
	}

	public void setCmd(short cmd) {
		this.cmd = cmd;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
	
}

package com.pactrex.common.model;

/**
 * 响应
 * @author Di.Lei
 * @date 2017年5月11日
 */
public class Response {

	private short module;
	
	private short cmd;
	
	private int resultCode = ResultCode.SUCCESS;
	
	private byte[] data;
	
	public Response() {
		super();
	}

	public Response(Request request) {
		this.module = request.getModule();
		this.cmd = request.getCmd();
	}
	
	public Response(short module, short cmd, byte[] data) {
		super();
		this.module = module;
		this.cmd = cmd;
		this.data = data;
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

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
	
}

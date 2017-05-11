package com.pactrex.common.codec;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

import com.pactrex.common.model.Request;

/**
 * 请求编码器
 * 
 * <pre>
 * 数据包格式
 * +——----——+——-----——+——----——+——----——+——-----——+
 * |  包头	|  模块号      |  命令号    |   长度     |   数据       |
 * +——----——+——-----——+——----——+——----——+——-----——+
 * </pre>
 * 
 * @author Di.Lei
 * @date 2017年5月11日
 */
public class RequestEncoder extends OneToOneEncoder {

	@Override
	protected Object encode(ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception {
		Request request = (Request) msg;

		ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();

		buffer.writeInt(ConstantValue.HEADER_FLAG);
		buffer.writeShort(request.getModule());
		buffer.writeShort(request.getCmd());
		buffer.writeInt(request.getData().length);
		buffer.writeBytes(request.getData());

		// 长度
		int length = request.getData() == null ? 0 : request.getData().length;
		if (length <= 0) {
			buffer.writeInt(length);
		} else {
			buffer.writeInt(length);
			buffer.writeBytes(request.getData());
		}
		return buffer;
	}

}

package cn.fetosoft.chat.connect.inbound;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.CharsetUtil;
import java.util.List;

/**
 * 消息解码器
 * @author guobingbing
 * @create 2020/12/11 13:30
 */
@ChannelHandler.Sharable
public class MessageDecode extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf bf, List<Object> list) throws Exception {
		if (bf.readableBytes() < 4) {
			return;
		}
		int readLeng = bf.readableBytes();
		//指令占了一位，剔除掉
		byte[] data = new byte[readLeng];
		bf.readBytes(data);
		String msg = new String(data, CharsetUtil.UTF_8);
		bf.clear();
		list.add(msg);
	}
}

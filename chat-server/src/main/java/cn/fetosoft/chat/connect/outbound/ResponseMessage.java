package cn.fetosoft.chat.connect.outbound;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.CharsetUtil;

/**
 * @author guobingbing
 * @create 2020/12/11 16:05
 */
@ChannelHandler.Sharable
public class ResponseMessage extends MessageToByteEncoder {


	@Override
	protected void encode(ChannelHandlerContext ctx, Object obj, ByteBuf byteBuf) throws Exception {
		byteBuf.writeBytes(obj.toString().getBytes(CharsetUtil.UTF_8));
	}
}

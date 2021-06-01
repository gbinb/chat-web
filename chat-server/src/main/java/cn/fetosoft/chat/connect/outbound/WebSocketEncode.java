package cn.fetosoft.chat.connect.outbound;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

/**
 * @author guobingbing
 * @create 2020/12/24 17:15
 */
public class WebSocketEncode extends ChannelOutboundHandlerAdapter {

	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		ctx.write(msg, promise);
	}
}

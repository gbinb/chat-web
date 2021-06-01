package cn.fetosoft.chat.connect.inbound;

import cn.fetosoft.chat.config.SystemConfig;
import cn.fetosoft.chat.connect.channel.ChatChannelFactory;
import cn.fetosoft.chat.connect.events.UserOnlineEvent;
import cn.fetosoft.chat.core.encrypt.JwtToken;
import cn.fetosoft.chat.core.enums.RespCode;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Token认证
 * @author guobingbing
 * @wechat t_gbinb
 * @create 2020/12/21 13:36
 */
@Component
@ChannelHandler.Sharable
public class RequestAuthenHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

	/**
	 * 日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(RequestAuthenHandler.class);
	@Autowired
	private SystemConfig systemConfig;
	@Autowired
	private ChatChannelFactory channelFactory;

	/**
	 *
	 */
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
		String uri = request.uri();
		String token = uri.substring(uri.lastIndexOf("/")+1);
		if(StringUtils.isNotBlank(token)){
			try{
				JwtToken.VerifyResult result = JwtToken.verifyToekn(token, this.systemConfig.getJwt().getSecretKey());
				if(result.isSuccess()) {
					String userId = result.getDataMap().get("userId");
					this.channelFactory.addChannel(userId, ctx.channel());
					ctx.fireChannelRead(request.setUri("/chat").retain());
					ctx.fireUserEventTriggered(new UserOnlineEvent(userId));
					logger.info("用户[{}]-[{}]加入会话！", userId, ctx.channel().id().asLongText());
				}else{
					ctx.writeAndFlush(this.buildResponse(request));
					ctx.close();
				}
			}catch(Exception e){
				ctx.writeAndFlush(this.buildResponse(request));
				ctx.close();
				logger.error("Invalid Token", e.getMessage());
			}
		}else{
			ctx.writeAndFlush(this.buildResponse(request));
			ctx.close();
		}
	}

	/**
	 * 设置响应信息
	 * @param request
	 * @return
	 */
	private FullHttpResponse buildResponse(FullHttpRequest request){
		JSONObject json = new JSONObject();
		json.put("code", RespCode.TOKEN_INVALID.getCode());
		json.put("message", "Invalid Token");
		FullHttpResponse response = new DefaultFullHttpResponse(request.protocolVersion(), HttpResponseStatus.valueOf(5004, json.toJSONString()));
		response.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json; charset=UTF-8");
		return response;
	}
}

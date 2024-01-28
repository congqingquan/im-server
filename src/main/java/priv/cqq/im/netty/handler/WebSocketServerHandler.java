package priv.cqq.im.netty.handler;

import cn.hutool.core.util.StrUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import lombok.extern.slf4j.Slf4j;
import org.cqq.openlibrary.core.util.EnumUtils;
import org.cqq.openlibrary.core.util.JSONUtils;
import org.cqq.oplibrary.web.exception.BusinessException;
import org.springframework.beans.BeanUtils;
import priv.cqq.im.domain.dto.IMUserAuthDTO;
import priv.cqq.im.domain.dto.WSChannelExtDTO;
import priv.cqq.im.netty.constants.NettyConstants;
import priv.cqq.im.netty.entity.message.Message;
import priv.cqq.im.netty.entity.message.WSMessage;
import priv.cqq.im.netty.enums.MessageTypeEnum;
import priv.cqq.im.netty.handler.message.MessageHandler;
import priv.cqq.im.netty.session.SessionManager;
import priv.cqq.im.service.im.WebSocketService;
import priv.cqq.im.util.NettyUtils;

/**
 * WebSocket 消息处理器
 *
 * @author CongQingquan
 */
@Slf4j
@ChannelHandler.Sharable
public class WebSocketServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private final WebSocketService webSocketService;

    public WebSocketServerHandler(WebSocketService webSocketService) {
        this.webSocketService = webSocketService;
    }

    // 当每个客户端连接后，触发该方法，即使 WebSocketServerHandler 是 Sharable 的
    // Gets called after the ChannelHandler was added to the actual context and it's ready to handle events.
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        log.info("WebSocketServerHandler added");
    }

    // 客户端正常离线：ctx.channel().close() 后先触发 channelInactive()，在触发 handlerRemoved
    // Gets called after the ChannelHandler was removed from the actual context and it doesn't handle events anymore
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        log.info("WebSocketServerHandler removed");
    }

    /**
     * 处理 WS 握手完成事件
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
            Channel channel = ctx.channel();
            IMUserAuthDTO authDTO = new IMUserAuthDTO();

            // 1. 解析 token 并返回内容
            String token = NettyUtils.getAttr(channel, NettyConstants.TOKEN);
            if (StrUtil.isNotBlank(token)) {
                authDTO = webSocketService.authentication(channel, token).orElse(new IMUserAuthDTO());
            }

            // 2. 加入全局会话
            WSChannelExtDTO extDTO = new WSChannelExtDTO();
            extDTO.setChannelId(channel.id().asLongText());
            BeanUtils.copyProperties(authDTO, extDTO);
            SessionManager.online(channel, extDTO);
        }
        super.userEventTriggered(ctx, evt);
    }


    // 读取客户端发送的请求报文
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        WSMessage wsMessage = JSONUtils.parseObject(msg.text(), WSMessage.class);
        MessageTypeEnum messageTypeEnum =
                EnumUtils.equalMatch(MessageTypeEnum.values(), MessageTypeEnum::getType, wsMessage.getType()).orElseThrow(() -> new BusinessException("No supported message type"));
        Message message = JSONUtils.parseObject(msg.text(), messageTypeEnum.getMessageClass());
        MessageHandler<? extends Message> messageHandler = messageTypeEnum.getSpringHandlerBean();
        messageHandler.castCallHandleMessage(message);
    }
}

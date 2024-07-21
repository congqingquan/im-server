package priv.cqq.im.netty.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cqq.openlibrary.common.exception.BusinessException;
import org.cqq.openlibrary.common.util.EnumUtils;
import org.cqq.openlibrary.common.util.JSONUtils;
import org.cqq.openlibrary.common.util.StringUtils;
import org.springframework.beans.BeanUtils;
import priv.cqq.im.domain.dto.IMUserAuthDTO;
import priv.cqq.im.domain.dto.WSChannelExtDTO;
import priv.cqq.im.netty.constants.NettyConstants;
import priv.cqq.im.netty.entity.message.Message;
import priv.cqq.im.netty.enums.MessageCategoryEnum;
import priv.cqq.im.netty.enums.UserTypeEnum;
import priv.cqq.im.netty.handler.message.MessageHandler;
import priv.cqq.im.netty.session.SessionManager;
import priv.cqq.im.service.WebSocketService;
import priv.cqq.im.util.NettyUtils;

import java.util.List;
import java.util.Map;

/**
 * WebSocket 消息处理器
 *
 * @author CongQingquan
 */
@Slf4j
@ChannelHandler.Sharable
@AllArgsConstructor
public class WebSocketServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    
    private final WebSocketService webSocketService;
    
    private final Map<MessageCategoryEnum, List<MessageHandler<? extends Message>>> messageHandlerMapping;
    
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
            WSChannelExtDTO extDTO = new WSChannelExtDTO();
            extDTO.setChannelId(channel.id().asLongText());
            // 1. 解析 token
            String token = NettyUtils.getAttr(channel, NettyConstants.TOKEN);
            // 1.1) 游客
            if (StringUtils.isBlank(token)) {
                extDTO.setType(UserTypeEnum.VISITOR.name());
            }
            // 1.2) 用户
            else {
                IMUserAuthDTO authDTO = webSocketService.authentication(channel, token).orElse(new IMUserAuthDTO());
                BeanUtils.copyProperties(authDTO, extDTO);
                extDTO.setType(UserTypeEnum.USER.name());
                NettyUtils.setAttr(channel, NettyConstants.UID, extDTO.getUserId());
            }
            
            // 2. 加入全局会话
            SessionManager.online(channel, extDTO);
            
            log.info("建立会话连接成功，用户信息 [{}]", extDTO);
        }
        super.userEventTriggered(ctx, evt);
    }
    
    // 统一处理C端发送的消息
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        // 1. 获取消息类型
        Message message = JSONUtils.parseObject(msg.text(), Message.class);
        MessageCategoryEnum messageCategoryEnum =
                EnumUtils.equalMatch(MessageCategoryEnum.values(), MessageCategoryEnum::name, message.getCategory())
                        .orElseThrow(() -> new BusinessException("No supported message type"));
        // 2. 根据消息类型获取对应的消息实体，并读取完整的消息体
        List<MessageHandler<? extends Message>> messageHandlers = messageHandlerMapping.get(messageCategoryEnum);
        for (MessageHandler<? extends Message> messageHandler : messageHandlers) {
            Message fullMessage = JSONUtils.parseObject(msg.text(), messageHandler.supportedMessageClass());
            messageHandler.handleMessage(ctx.channel(), fullMessage);
        }
    }
}

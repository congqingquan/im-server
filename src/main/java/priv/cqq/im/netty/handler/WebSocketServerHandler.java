package priv.cqq.im.netty.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cqq.openlibrary.common.util.JSONUtils;
import org.cqq.openlibrary.common.util.StringUtils;
import org.springframework.beans.BeanUtils;
import priv.cqq.im.domain.dto.IMUserAuthDTO;
import priv.cqq.im.domain.dto.WSChannelExtDTO;
import priv.cqq.im.netty.constants.NettyConstants;
import priv.cqq.im.netty.entity.message.Message;
import priv.cqq.im.netty.enums.UserTypeEnum;
import priv.cqq.im.netty.handler.message.MessageHandlerManager;
import priv.cqq.im.netty.session.SessionManager;
import priv.cqq.im.service.WebSocketService;
import priv.cqq.im.util.NettyUtils;
import priv.cqq.im.util.RedisPublisher;

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
    
    private final MessageHandlerManager messageHandlerManager;
    
    private final RedisPublisher redisPublisher;
    
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
    
    /**
     * 统一处理 C 端发送的消息:
     *
     * 1. 如果当前服务节点存在 target channel 则直接使用，否则广播其他服务节点
     * 2. MessageHandler 在推送消息时同理，先检查本地是否存在 target channel 否则广播其他服务节点 (priv.cqq.im.netty.handler.message.MessageHandler#publishMessage(java.lang.Long, java.lang.String))
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        String originMessage = msg.text();
        Message message = JSONUtils.parseObject(originMessage, Message.class);
        Channel targetUserChannel = SessionManager.get(message.getTargetUserId());
        if (targetUserChannel == null) {
            log.info("目标用户 [{}] 未链接到当前节点，将消息广播推送到其他 Server 节点", message.getTargetUserId());
            redisPublisher.publish(NettyConstants.DISTRIBUTED_IM_MESSAGE_TOPIC, originMessage);
            return;
        }
        // if targetUserChannel != null > targetUserChannel == ctx.channel()
        messageHandlerManager.handleMessage(targetUserChannel, (msg.text()));
    }
}

// 如何能不广播到自己？这样就不需要判断 channel 是否存在本地服务了。
// > session 统一管理。
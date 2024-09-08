package priv.cqq.im.netty.handler.message;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cqq.openlibrary.common.util.JSONUtils;
import org.cqq.openlibrary.common.util.spring.SpringUtils;
import org.springframework.stereotype.Component;
import priv.cqq.im.manager.IMChatGroupManager;
import priv.cqq.im.manager.IMChatGroupMemberManager;
import priv.cqq.im.manager.IMChatMessageManager;
import priv.cqq.im.netty.constants.NettyConstants;
import priv.cqq.im.netty.entity.message.Message;
import priv.cqq.im.netty.enums.MessageCategoryEnum;
import priv.cqq.im.netty.session.SessionManager;
import priv.cqq.im.util.RedisPublisher;

/**
 * Message handler
 *
 * @author CongQingquan
 */
@Slf4j
@Component
@AllArgsConstructor
public abstract class MessageHandler<T extends Message> {
    
    protected final IMChatMessageManager chatMessageManager;
    
    protected final IMChatGroupManager chatGroupManager;
    
    protected final IMChatGroupMemberManager chatGroupMemberManager;
    
    protected final RedisPublisher redisPublisher;
    
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void handleMessage(Channel channel, Message message) throws ClassCastException {
        MessageHandler currentProxy = SpringUtils.getCurrentProxy(MessageHandler.class);
        if (currentProxy != null) {
            currentProxy.doHandleMessage(channel, message);
        } else {
            doHandleMessage(channel, (T) message);
        }
    }
    
    public abstract MessageCategoryEnum supportedCategory();
    
    public abstract Class<T> supportedMessageClass();
    
    public abstract void doHandleMessage(Channel channel, T message);
    
    protected void publishJsonMessage(Long targetId, String jsonMessage) {
        
        if (!JSONUtils.isJson(jsonMessage)) {
            throw new IllegalArgumentException("Invalid json message format");
        }
        
        Channel targetUserChannel = SessionManager.get(targetId);
        if (targetUserChannel == null) {
            log.info("目标用户 [{}] 未链接到当前节点，将消息广播推送到其他 Server 节点", targetId);
            redisPublisher.publish(NettyConstants.DISTRIBUTED_IM_MESSAGE_TOPIC, jsonMessage);
            return;
        }
        targetUserChannel.writeAndFlush(new TextWebSocketFrame(jsonMessage));
    }
}
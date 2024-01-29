package priv.cqq.im.netty.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.cqq.openlibrary.spring.SpringUtils;
import priv.cqq.im.netty.entity.message.DirectMessage;
import priv.cqq.im.netty.entity.message.GroupMessage;
import priv.cqq.im.netty.entity.message.Message;
import priv.cqq.im.netty.handler.message.DirectMessageHandler;
import priv.cqq.im.netty.handler.message.GroupMessageHandler;
import priv.cqq.im.netty.handler.message.HeartbeatMessageHandler;
import priv.cqq.im.netty.handler.message.MessageHandler;

/**
 * Message type enum
 *
 * @author CongQingquan
 */
@Getter
@AllArgsConstructor
public enum MessageTypeEnum {
    
    HEARTBEAT(0, "Heartbeat message", DirectMessage.class, HeartbeatMessageHandler.class),
    DMS(1, "Direct message", DirectMessage.class, DirectMessageHandler.class),
    GROUP(2, "Group message", GroupMessage.class, GroupMessageHandler.class),
    ;
    
    private final Integer type;
    
    private final String description;
    
    private final Class<? extends Message> messageClass;
    
    private final Class<? extends MessageHandler<? extends Message>> handlerClass;
    
    public MessageHandler<? extends Message> getSpringHandlerBean() {
        return SpringUtils.getBean(getHandlerClass());
    }
}
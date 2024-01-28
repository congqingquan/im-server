package priv.cqq.im.netty.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.cqq.openlibrary.spring.SpringUtils;
import priv.cqq.im.netty.entity.message.DMSMessage;
import priv.cqq.im.netty.entity.message.GroupMessage;
import priv.cqq.im.netty.entity.message.Message;
import priv.cqq.im.netty.handler.message.DMSMessageHandler;
import priv.cqq.im.netty.handler.message.GroupMessageHandler;
import priv.cqq.im.netty.handler.message.MessageHandler;

/**
 * Message type enum
 *
 * @author CongQingquan
 */
@Getter
@AllArgsConstructor
public enum MessageTypeEnum {
    
    HEARTBEAT(0, "Heartbeat message", DMSMessage.class, DMSMessageHandler.class),
    DMS(1, "Direct message", DMSMessage.class, DMSMessageHandler.class),
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
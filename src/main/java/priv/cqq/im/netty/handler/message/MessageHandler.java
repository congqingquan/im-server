package priv.cqq.im.netty.handler.message;

import io.netty.channel.Channel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cqq.openlibrary.common.util.spring.SpringUtils;
import org.springframework.stereotype.Component;
import priv.cqq.im.manager.IMChatGroupManager;
import priv.cqq.im.manager.IMChatGroupMemberManager;
import priv.cqq.im.manager.IMChatMessageManager;
import priv.cqq.im.netty.entity.message.Message;
import priv.cqq.im.netty.enums.MessageCategoryEnum;

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
}
package priv.cqq.im.netty.handler.message;

import io.netty.channel.Channel;
import org.springframework.stereotype.Component;
import priv.cqq.im.manager.IMChatGroupManager;
import priv.cqq.im.manager.IMChatGroupMemberManager;
import priv.cqq.im.manager.IMChatMessageManager;
import priv.cqq.im.netty.entity.message.HeartbeatMessage;
import priv.cqq.im.netty.enums.MessageCategoryEnum;

/**
 * Heartbeat message handler
 *
 * @author CongQingquan
 */
@Component
public class HeartbeatMessageHandler extends MessageHandler<HeartbeatMessage> {
    
    public HeartbeatMessageHandler(IMChatMessageManager chatMessageManager, IMChatGroupManager chatGroupManager, IMChatGroupMemberManager chatGroupMemberManager) {
        super(chatMessageManager, chatGroupManager, chatGroupMemberManager);
    }
    
    @Override
    public MessageCategoryEnum supportedCategory() {
        return MessageCategoryEnum.HEARTBEAT;
    }
    
    @Override
    public Class<HeartbeatMessage> supportedMessageClass() {
        return HeartbeatMessage.class;
    }
    
    @Override
    public void doHandleMessage(Channel channel, HeartbeatMessage message) {
    
    }
}

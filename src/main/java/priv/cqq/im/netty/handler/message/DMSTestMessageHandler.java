package priv.cqq.im.netty.handler.message;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.cqq.openlibrary.common.util.JSONUtils;
import org.springframework.stereotype.Component;
import priv.cqq.im.manager.IMChatGroupManager;
import priv.cqq.im.manager.IMChatGroupMemberManager;
import priv.cqq.im.manager.IMChatMessageManager;
import priv.cqq.im.netty.entity.message.DMSTestMessage;
import priv.cqq.im.netty.enums.MessageCategoryEnum;
import priv.cqq.im.util.RedisPublisher;

/**
 * DMS test message handler
 *
 * @author CongQingquan
 */
@Slf4j
@Component
public class DMSTestMessageHandler extends MessageHandler<DMSTestMessage> {
    
    public DMSTestMessageHandler(IMChatMessageManager chatMessageManager,
                                 IMChatGroupManager chatGroupManager,
                                 IMChatGroupMemberManager chatGroupMemberManager,
                                 RedisPublisher redisPublisher) {
        super(chatMessageManager, chatGroupManager, chatGroupMemberManager, redisPublisher);
    }
    
    @Override
    public MessageCategoryEnum supportedCategory() {
        return MessageCategoryEnum.DMS_TEST;
    }
    
    @Override
    public Class<DMSTestMessage> supportedMessageClass() {
        return DMSTestMessage.class;
    }
    
    @Override
    public void doHandleMessage(Channel channel, DMSTestMessage message) {
        // biz operation
        log.info("DMS test message handler: [{}]", JSONUtils.toJSONString(message));
        
        String receivedMessage = String.format("Received: [%s]", message.getContent());
        message.setContent(receivedMessage);
        
        // send to client channel
        publishJsonMessage(message.getTargetUserId(), JSONUtils.toJSONString(message));
        
        // 自动回复的问题: 如果都为同类型消息，那么都会被该 DMSTestMessageHandler 处理，会循环的互换 fromUserId 与 targetUserId 导致死循环。
        // 解决方式：需要在定义一种自动回复的消息格式，由另一个不会互换 fromUserId 与 targetUserId 的消息处理器进行处理。
        // reply sender (x)
        //        DMSTestMessage replyMessage = ReturnableBeanUtils.copyProperties(message, DMSTestMessage::new);
        //        Long fromUserId = message.getFromUserId();
        //        Long targetUserId = message.getTargetUserId();
        //        replyMessage.setFromUserId(targetUserId);
        //        replyMessage.setTargetUserId(fromUserId);
        //        publishJsonMessage(replyMessage.getTargetUserId(), JSONUtils.toJSONString(replyMessage));
    }
}

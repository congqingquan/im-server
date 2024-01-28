package priv.cqq.im.netty.handler.message;

import org.springframework.stereotype.Component;
import priv.cqq.im.netty.entity.message.GroupMessage;

/**
 * Group message handler
 *
 * @author CongQingquan
 */
@Component
public class GroupMessageHandler extends MessageHandler<GroupMessage> {
    
    @Override
    public void handleMessage(GroupMessage message) {
    
    }
}

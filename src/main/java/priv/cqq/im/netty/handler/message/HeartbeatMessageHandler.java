package priv.cqq.im.netty.handler.message;

import org.springframework.stereotype.Component;
import priv.cqq.im.netty.entity.message.HeartbeatMessage;

/**
 * Heartbeat message handler
 *
 * @author CongQingquan
 */
@Component
public class HeartbeatMessageHandler extends MessageHandler<HeartbeatMessage> {
    
    @Override
    public void handleMessage(HeartbeatMessage message) {
    
    }
}

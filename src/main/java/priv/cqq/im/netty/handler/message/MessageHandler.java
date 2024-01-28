package priv.cqq.im.netty.handler.message;

import lombok.extern.slf4j.Slf4j;
import priv.cqq.im.netty.entity.message.Message;

/**
 * Message handler
 *
 * @author CongQingquan
 */
@Slf4j
public abstract class MessageHandler<T extends Message> {
    
    public void castCallHandleMessage(Message message) throws ClassCastException {
        handleMessage((T) message);
    }
    
    public abstract void handleMessage(T message);
}

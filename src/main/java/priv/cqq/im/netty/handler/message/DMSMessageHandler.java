package priv.cqq.im.netty.handler.message;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.stereotype.Component;
import priv.cqq.im.netty.entity.message.DMSMessage;
import priv.cqq.im.netty.session.SessionManager;

/**
 * Direct message handler
 *
 * @author CongQingquan
 */
@Component
public class DMSMessageHandler extends MessageHandler<DMSMessage> {
    
    @Override
    public void handleMessage(DMSMessage message) {
        Channel channel = SessionManager.get(message.getTarget());
        channel.writeAndFlush(new TextWebSocketFrame(message.getContent()));
    }
}

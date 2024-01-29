package priv.cqq.im.netty.handler.message;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import priv.cqq.im.manager.ChatMessageManager;
import priv.cqq.im.netty.entity.message.DirectMessage;
import priv.cqq.im.netty.session.SessionManager;

/**
 * Direct message handler
 *
 * @author CongQingquan
 */
@Component
@AllArgsConstructor
public class DirectMessageHandler extends MessageHandler<DirectMessage> {
    
    private final ChatMessageManager chatMessageManager;
    
    @Override
    public void handleMessage(DirectMessage message) {
        chatMessageManager.saveDirectMessage(message);
        Channel channel = SessionManager.get(message.getTarget());
        channel.writeAndFlush(new TextWebSocketFrame(message.getContent()));
    }
}

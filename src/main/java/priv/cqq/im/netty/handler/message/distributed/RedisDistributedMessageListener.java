package priv.cqq.im.netty.handler.message.distributed;

import io.netty.channel.Channel;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cqq.openlibrary.common.util.JSONUtils;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.codec.SerializationCodec;
import org.springframework.stereotype.Component;
import priv.cqq.im.netty.constants.NettyConstants;
import priv.cqq.im.netty.entity.message.Message;
import priv.cqq.im.netty.handler.message.MessageHandlerManager;
import priv.cqq.im.netty.session.SessionManager;

/**
 * Redis 分布式消息监听器
 *
 * @author CongQingquan
 */
@Slf4j
@Component
@AllArgsConstructor
public class RedisDistributedMessageListener {

    private final RedissonClient redissonClient;
    
    private final MessageHandlerManager messageHandlerManager;
    
    @PostConstruct
    public void listen() {
        RTopic topic = redissonClient.getTopic(NettyConstants.DISTRIBUTED_IM_MESSAGE_TOPIC, new SerializationCodec());
        
        log.info("监听 [{}] 分布式消息成功", String.join(", ", topic.getChannelNames()));
        
        topic.addListener(String.class, (channel, messageString) -> {
            
            log.info("分布式消息已抵达 [{}]", messageString);
            
            Message message = JSONUtils.parseObject(messageString, Message.class);
            Channel targetUserChannel = SessionManager.get(message.getTargetUserId());
            if (targetUserChannel == null) {
                log.info("目标用户 [{}] 未链接到当前节点，监听结束", message.getTargetUserId());
                return;
            }
            messageHandlerManager.handleMessage(targetUserChannel, messageString);
        });
    }
}
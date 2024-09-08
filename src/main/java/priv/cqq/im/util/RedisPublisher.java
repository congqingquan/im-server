package priv.cqq.im.util;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.codec.SerializationCodec;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Redis 消息发布器
 *
 * @author CongQingquan
 */
@Slf4j
@Component
public class RedisPublisher {
    
    private final RedissonClient redissonClient;
    
    private final Map<String, RTopic> topicMap;
    
    public RedisPublisher(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
        this.topicMap = new ConcurrentHashMap<>();
    }
    
    public void publish(String topicName, Object message) {
        RTopic publishTopic =
                topicMap.compute(
                        topicName,
                        (tn, rTopic) -> rTopic == null ? redissonClient.getTopic(tn, new SerializationCodec()) : rTopic
                );
        if (publishTopic == null) {
            throw new IllegalArgumentException("Invalid topic name");
        }
        publishTopic.publish(message);
        log.info("推送消息成功");
    }
}
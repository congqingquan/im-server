package priv.cqq.im.netty.session;

import io.netty.channel.Channel;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Session manager
 *
 * @author Qingquan.Cong
 */
public class SessionManager {

    private static final ConcurrentMap<Channel, Long> CHANNEL_USER_ID_MAP = new ConcurrentHashMap<>(16);
    private static final ConcurrentMap<Long, Channel> USER_ID_CHANNEL_MAP = new ConcurrentHashMap<>(16);

    public static void online(Channel channel, Long userId) {
        if (channel == null) {
            throw new IllegalArgumentException("Channel cannot be null");
        }
        CHANNEL_USER_ID_MAP.put(channel, userId);
        if (userId != null) {
            USER_ID_CHANNEL_MAP.put(userId, channel);
        }
    }

    public static void offline(Channel channel) {
        Long userId = CHANNEL_USER_ID_MAP.remove(channel);
        if (userId != null) {
            USER_ID_CHANNEL_MAP.remove(userId);
        }
    }

    public static Long get(Channel channel) {
        return CHANNEL_USER_ID_MAP.get(channel);
    }

    public static Channel get(Long userId) {
        return USER_ID_CHANNEL_MAP.get(userId);
    }
}
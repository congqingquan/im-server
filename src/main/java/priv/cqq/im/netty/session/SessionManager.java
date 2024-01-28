package priv.cqq.im.netty.session;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import priv.cqq.im.domain.dto.WSChannelExtDTO;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Session manager
 *
 * @author Qingquan.Cong
 */
@Slf4j
public class SessionManager {

    private static final ConcurrentMap<Channel, WSChannelExtDTO> ONLINE_CHANNEL_MAP = new ConcurrentHashMap<>(16);
    
    private static final ConcurrentMap<Long, Channel> ONLINE_UID_CHANNEL_MAP = new ConcurrentHashMap<>(16);
    
    public static void online(Channel channel, WSChannelExtDTO extDTO) {
        if (channel == null) {
            throw new IllegalArgumentException("Channel cannot be null");
        }
        if (extDTO == null) {
            throw new IllegalArgumentException("WSChannelExtDTO cannot be null");
        }
        log.info("WSChannel online. Channel: {}, ChannelExtInfo: {}", channel, extDTO);
        ONLINE_CHANNEL_MAP.put(channel, extDTO);
        if (extDTO.getUserId() != null) {
            ONLINE_UID_CHANNEL_MAP.put(extDTO.getUserId(), channel);
        }
    }

    public static void offline(Channel channel) {
        WSChannelExtDTO extDTO = ONLINE_CHANNEL_MAP.remove(channel);
        log.info("WSChannel offline. Channel: {}, ChannelExtInfo: {}", channel, extDTO);
        if (extDTO.getUserId() != null) {
            ONLINE_UID_CHANNEL_MAP.remove(extDTO.getUserId());
        }
        channel.close();
    }

    public static WSChannelExtDTO get(Channel channel) {
        return ONLINE_CHANNEL_MAP.get(channel);
    }
    
    public static Channel get(Long userId) {
        return ONLINE_UID_CHANNEL_MAP.get(userId);
    }
    
}
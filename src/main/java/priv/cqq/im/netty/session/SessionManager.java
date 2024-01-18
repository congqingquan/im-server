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
    private static final ConcurrentMap<WSChannelExtDTO, Channel> REVERSED_ONLINE_CHANNEL_MAP = new ConcurrentHashMap<>(16);

    public static void online(Channel channel, WSChannelExtDTO extDTO) {
        if (channel == null) {
            throw new IllegalArgumentException("Channel cannot be null");
        }
        if (extDTO == null) {
            throw new IllegalArgumentException("WSChannelExtDTO cannot be null");
        }
        log.info("WSChannel online. Channel: {}, ChannelExtInfo: {}", channel, extDTO);
        ONLINE_CHANNEL_MAP.put(channel, extDTO);
        REVERSED_ONLINE_CHANNEL_MAP.put(extDTO, channel);
    }

    public static void offline(Channel channel) {
        WSChannelExtDTO extDTO = ONLINE_CHANNEL_MAP.remove(channel);
        log.info("WSChannel offline. Channel: {}, ChannelExtInfo: {}", channel, extDTO);
        if (extDTO != null) {
            REVERSED_ONLINE_CHANNEL_MAP.remove(extDTO);
        }
        channel.close();
    }

    public static WSChannelExtDTO get(Channel channel) {
        return ONLINE_CHANNEL_MAP.get(channel);
    }

    public static Channel get(WSChannelExtDTO extDTO) {
        return REVERSED_ONLINE_CHANNEL_MAP.get(extDTO);
    }
}
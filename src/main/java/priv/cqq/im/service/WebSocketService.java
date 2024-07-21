package priv.cqq.im.service;

import io.netty.channel.Channel;
import priv.cqq.im.domain.dto.IMUserAuthDTO;

import java.util.Optional;

/**
 * WebSocket service
 *
 * @author Qingquan.Cong
 */
public interface WebSocketService {

    /**
     * 认证
     */
    Optional<IMUserAuthDTO> authentication(Channel channel, String token);
}
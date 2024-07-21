package priv.cqq.im.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwt;
import io.netty.channel.Channel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cqq.openlibrary.common.util.JSONUtils;
import org.cqq.openlibrary.common.util.JWSUtils;
import org.springframework.stereotype.Service;
import priv.cqq.im.config.JWSAuthenticationConfig;
import priv.cqq.im.domain.dto.IMUserAuthDTO;
import priv.cqq.im.service.WebSocketService;

import java.util.Optional;

/**
 * WebSocket service
 *
 * @author Qingquan.Cong
 */
@Slf4j
@Service
@AllArgsConstructor
public class WebSocketServiceImpl implements WebSocketService {

    private final JWSAuthenticationConfig jwsAuthenticationConfig;

    @Override
    public Optional<IMUserAuthDTO> authentication(Channel channel, String token) {
        
        JWSUtils.ParseResult parseResult = JWSUtils.parseToken(token, jwsAuthenticationConfig.getSecretKey(), jwsAuthenticationConfig.getDuration());
        
        JWSUtils.TokenStatus tokenStatus = parseResult.getTokenStatus();
        // TODO 发送 WS 消息通知前端 token 过期，重新登录
        if (JWSUtils.TokenStatus.VALID != tokenStatus) {
            log.info("发送 WS 消息通知前端 token 过期，重新登录");
            return Optional.empty();
        }

        Jwt<Header<?>, Claims> claims = parseResult.getJwt();
        return Optional.of(
                JSONUtils.parseObject(claims.getBody().get(jwsAuthenticationConfig.getUserPayloadKey()).toString(), IMUserAuthDTO.class)
        );
    }
}
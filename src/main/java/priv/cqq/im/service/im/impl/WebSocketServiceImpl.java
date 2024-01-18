package priv.cqq.im.service.im.impl;

import cn.hutool.json.JSONUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.SignatureAlgorithm;
import io.netty.channel.Channel;
import io.vavr.Tuple2;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cqq.oplibrary.web.util.JWSUtils;
import org.springframework.stereotype.Service;
import priv.cqq.im.config.JWSConfig;
import priv.cqq.im.domain.dto.IMUserAuthDTO;
import priv.cqq.im.service.im.WebSocketService;

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

    private final JWSConfig jwsConfig;

    @Override
    public Optional<IMUserAuthDTO> authentication(Channel channel, String token) {

        Tuple2<JWSUtils.TokenStatus, Jwt<Header<?>, Claims>> parseAndGetTokenStatus =
                JWSUtils.parseAndGetTokenStatus(token, SignatureAlgorithm.forName(jwsConfig.getSignatureAlgorithm()), jwsConfig.getSecretKey(), 0);

        JWSUtils.TokenStatus tokenStatus = parseAndGetTokenStatus._1;
        // TODO 发送 WS 消息通知前端 token 过期，重新登录
        if (JWSUtils.TokenStatus.VALID != tokenStatus) {
            log.info("发送 WS 消息通知前端 token 过期，重新登录");
            return Optional.empty();
        }

        Jwt<Header<?>, Claims> claims = parseAndGetTokenStatus._2;
        return Optional.of(
                JSONUtil.toBean(claims.getBody().get(jwsConfig.getUserPayloadKey()).toString(), IMUserAuthDTO.class)
        );
    }
}
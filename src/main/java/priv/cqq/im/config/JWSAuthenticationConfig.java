package priv.cqq.im.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * JWS 鉴权配置
 *
 * @author Qingquan.Cong
 */
@Data
@ConfigurationProperties(prefix = "auth.jws")
@Component
public class JWSAuthenticationConfig {

    private String authHeader;
    private String userPayloadKey;
    private String signatureAlgorithm;
    private String secretKey;
    private Long duration;
}
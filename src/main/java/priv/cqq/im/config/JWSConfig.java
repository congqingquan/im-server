package priv.cqq.im.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * JWS config
 *
 * @author CongQingquan
 */
@Data
@Component
@ConfigurationProperties(prefix = "auth.jws")
public class JWSConfig {

    private String authHeader;

    private String userPayloadKey;

    private String signatureAlgorithm;

    private String secretKey;

    private Long duration;
}
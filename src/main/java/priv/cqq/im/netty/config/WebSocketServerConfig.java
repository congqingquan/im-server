package priv.cqq.im.netty.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * WebSocket server config
 *
 * @author CongQingquan
 */
@Data
@Component
@ConfigurationProperties(prefix = "websocket-server")
public class WebSocketServerConfig {

    private Integer port;
    
    private Integer bossGroup;
    
    private Integer workGroup;
    
    private Integer aggregatorMaxContentLength;

    @Value("${websocket-server.client-idle.receive-timeout-seconds}")
    private Integer receiveTimeoutSeconds;

    @Value("${websocket-server.client-idle.send-timeout-seconds}")
    private Integer sendTimeoutSeconds;

    @Value("${websocket-server.client-idle.all-timeout-seconds}")
    private Integer allTimeoutSeconds;
}
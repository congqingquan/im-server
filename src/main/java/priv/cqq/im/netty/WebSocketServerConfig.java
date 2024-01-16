package priv.cqq.im.netty;

import lombok.Data;
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
}

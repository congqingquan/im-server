package priv.cqq.im.netty.handler;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * Client channel 空闲检测处理器
 *
 * @author CongQingquan
 */
@Slf4j
@ChannelHandler.Sharable
public class ClientIdleListenHandler extends ChannelDuplexHandler {

    private final Integer receiveTimeoutSeconds;

    private final Integer sendTimeoutSeconds;

    private final Integer allTimeoutSeconds;

    public ClientIdleListenHandler(Integer receiveTimeoutSeconds, Integer sendTimeoutSeconds, Integer allTimeoutSeconds) {
        this.receiveTimeoutSeconds = receiveTimeoutSeconds;
        this.sendTimeoutSeconds = sendTimeoutSeconds;
        this.allTimeoutSeconds = allTimeoutSeconds;
    }

    // 监听 receiveTimeoutSeconds 内读空闲的 client channel
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (IdleState.READER_IDLE == event.state()) {
                log.info("已经 {} 秒没有接收到 Client [{}] 消息，即将踢掉用户", receiveTimeoutSeconds, ctx.channel().id());
                ctx.channel().close();
            }
        }
        super.userEventTriggered(ctx, evt);
    }

    public IdleStateHandler createNettyIdleStateHandler() {
        return new IdleStateHandler(receiveTimeoutSeconds, sendTimeoutSeconds, allTimeoutSeconds);
    }
}
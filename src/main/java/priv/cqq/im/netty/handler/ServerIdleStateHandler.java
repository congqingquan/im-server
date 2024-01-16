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
public class ServerIdleStateHandler extends ChannelDuplexHandler {

    private static final Integer RECEIVE_TIMEOUT_SECONDS = 60;

    private static final Integer SEND_TIMEOUT_SECONDS = 0;

    private static final Integer ALL_TIMEOUT_SECONDS = 0;

    // 监听 RECEIVE_TIMEOUT_SECONDS 内，server 无法读取到消息的 client channel
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (IdleState.READER_IDLE == event.state()) {
                log.info("已经 {} 秒没有接收到 Client [{}] 消息，即将踢掉用户", RECEIVE_TIMEOUT_SECONDS, ctx.channel().id());
                // 关闭用户的连接
                ctx.channel().close();
            }
        }
        super.userEventTriggered(ctx, evt);
    }

    public static IdleStateHandler createNettyServerIdleStateHandler() {
        return new IdleStateHandler(RECEIVE_TIMEOUT_SECONDS, SEND_TIMEOUT_SECONDS, ALL_TIMEOUT_SECONDS);
    }
}
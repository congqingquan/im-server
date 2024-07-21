package priv.cqq.im.netty.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import priv.cqq.im.netty.session.SessionManager;

/**
 * Client channel 断连处理器
 *
 * @author CongQingquan
 */
@Slf4j
@ChannelHandler.Sharable
public class QuitHandler extends ChannelInboundHandlerAdapter {

    // 监听正常退出: ctx.channel().close()
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.warn("{} 触发正常断开连接", ctx.channel());
        SessionManager.offline(ctx.channel());
        super.channelInactive(ctx);
    }

    // 监听异常（最终触发正常断开链接）
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("{} 发生运行时异常，或异常断开连接", ctx.channel(), cause);
        ctx.writeAndFlush(cause.getMessage());
        super.exceptionCaught(ctx, cause);
    }
}
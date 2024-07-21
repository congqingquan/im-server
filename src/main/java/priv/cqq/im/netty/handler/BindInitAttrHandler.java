package priv.cqq.im.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.cqq.openlibrary.common.util.NetUtils;
import priv.cqq.im.netty.constants.NettyConstants;
import priv.cqq.im.util.NettyUtils;

import java.net.InetSocketAddress;
import java.util.Map;

/**
 * Client chanel 初始属性绑定处理器
 *
 * @author CongQingquan
 */
@Slf4j
public class BindInitAttrHandler extends ChannelInboundHandlerAdapter {
    
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest request) {
            String uri = request.uri();
            Map<String, String> getRequestParam = NetUtils.parseGetRequestParam(uri);

            // 1. token
            String token = getRequestParam.get("token");
            NettyUtils.setAttr(ctx.channel(), NettyConstants.TOKEN, token);
            
            // 2. ip
            HttpHeaders headers = request.headers();
            String ip = headers.get("X-Real-IP");
            // 如果没经过nginx，就直接获取远端地址
            if (StringUtils.isEmpty(ip)) {
                InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();
                ip = address.getAddress().getHostAddress();
            }
            NettyUtils.setAttr(ctx.channel(), NettyConstants.IP, ip);

            log.info("用户发起 WS 协议连接，ip: [{}], token: [{}]", ip, token);

            // 3. remove this
            ctx.pipeline().remove(this);
        }
        ctx.fireChannelRead(msg);
    }
}
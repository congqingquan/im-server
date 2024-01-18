package priv.cqq.im.netty.handler;

import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.core.net.url.UrlQuery;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import priv.cqq.im.netty.constants.NettyConstants;
import priv.cqq.im.util.NettyUtils;

import java.net.InetSocketAddress;

/**
 * Client chanel 初始属性绑定处理器
 *
 * @author CongQingquan
 */
@Slf4j
public class BindInitAttrHandler extends ChannelInboundHandlerAdapter {
    
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            FullHttpRequest request = (FullHttpRequest) msg;
            UrlBuilder urlBuilder = UrlBuilder.ofHttp(request.uri());
            UrlQuery urlQuery = urlBuilder.getQuery();
            
            // 1. token
            CharSequence token = urlQuery.get("token");
            String tokenStr = token == null ? null : token.toString();
            NettyUtils.setAttr(ctx.channel(), NettyConstants.TOKEN, tokenStr);

            // 2. ip
            request.setUri(urlBuilder.getPath().toString());
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
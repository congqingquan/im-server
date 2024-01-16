package priv.cqq.im.netty.handler;

import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.core.net.url.UrlQuery;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import org.apache.commons.lang3.StringUtils;

import java.net.InetSocketAddress;

/**
 * Client chanel 初始属性绑定处理器
 *
 * @author CongQingquan
 */
public class BindInitAttrHandler extends ChannelInboundHandlerAdapter {
    
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            FullHttpRequest request = (FullHttpRequest) msg;
            UrlBuilder urlBuilder = UrlBuilder.ofHttp(request.uri());
            UrlQuery urlQuery = urlBuilder.getQuery();
            
            // 1. token
            CharSequence token = urlQuery.get("token");
            Attribute<String> tokenAttr = ctx.channel().attr(AttributeKey.valueOf("token"));
            tokenAttr.set(token == null ? null : token.toString());

            // 2. ip
            request.setUri(urlBuilder.getPath().toString());
            HttpHeaders headers = request.headers();
            String ip = headers.get("X-Real-IP");
            // 如果没经过nginx，就直接获取远端地址
            if (StringUtils.isEmpty(ip)) {
                InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();
                ip = address.getAddress().getHostAddress();
            }
            Attribute<String> ipAttr = ctx.channel().attr(AttributeKey.valueOf("ip"));
            ipAttr.set(ip);

            ctx.pipeline().remove(this);
        }
        ctx.fireChannelRead(msg);
    }

}
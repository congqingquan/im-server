package priv.cqq.im.netty.handler;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import lombok.extern.slf4j.Slf4j;
import priv.cqq.im.domain.dto.IMUserAuthDTO;
import priv.cqq.im.netty.constants.NettyConstants;
import priv.cqq.im.netty.session.SessionManager;
import priv.cqq.im.service.im.WebSocketService;
import priv.cqq.im.util.NettyUtils;

/**
 * WebSocket 消息处理器
 *
 * @author CongQingquan
 */
@Slf4j
@ChannelHandler.Sharable
public class WebSocketServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private final WebSocketService webSocketService;

    public WebSocketServerHandler(WebSocketService webSocketService) {
        this.webSocketService = webSocketService;
    }

    // 当每个客户端连接后，触发该方法，即使 WebSocketServerHandler 是 Sharable 的
    // Gets called after the ChannelHandler was added to the actual context and it's ready to handle events.
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        log.info("WebSocketServerHandler added");
    }

    // 客户端正常离线：ctx.channel().close() 后先触发 channelInactive()，在触发 handlerRemoved
    // Gets called after the ChannelHandler was removed from the actual context and it doesn't handle events anymore
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        log.info("WebSocketServerHandler removed");
    }

    /**
     * 处理 WS 握手完成事件
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
            Channel channel = ctx.channel();
            Long userId = null;

            String token = NettyUtils.getAttr(channel, NettyConstants.TOKEN);
            if (StrUtil.isNotBlank(token)) {
                userId = webSocketService.authentication(ctx.channel(), token).map(IMUserAuthDTO::getUserId).orElse(null);
            }

            SessionManager.online(ctx.channel(), userId);
        }
        super.userEventTriggered(ctx, evt);
    }


    // 读取客户端发送的请求报文
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        JSON parse = JSONUtil.parse(msg.text());
        log.info(parse.toString());
//        WSBaseReq wsBaseReq = JSONUtil.toBean(msg.text(), WSBaseReq.class);
//        WSReqTypeEnum wsReqTypeEnum = WSReqTypeEnum.of(wsBaseReq.getType());
//        switch (wsReqTypeEnum) {
//            case LOGIN:
//                this.webSocketService.handleLoginReq(ctx.channel());
//                log.info("请求二维码 = " + msg.text());
//                break;
//            case HEARTBEAT:
//                break;
//            default:
//                log.info("未知类型");
//        }
    }
}

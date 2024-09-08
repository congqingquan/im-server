package priv.cqq.im.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.NettyRuntime;
import io.netty.util.concurrent.Future;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;
import priv.cqq.im.netty.config.WebSocketServerConfig;
import priv.cqq.im.netty.handler.BindInitAttrHandler;
import priv.cqq.im.netty.handler.ClientIdleListenHandler;
import priv.cqq.im.netty.handler.QuitHandler;
import priv.cqq.im.netty.handler.WebSocketServerHandler;
import priv.cqq.im.netty.handler.message.MessageHandlerManager;
import priv.cqq.im.service.WebSocketService;
import priv.cqq.im.util.RedisPublisher;


/**
 * WebSocket server
 *
 * @author CongQingquan
 */
@Slf4j
@Component
public class WebSocketServer {
    
    // 前端测试网站：https://wstool.js.org/
    
    private final EventLoopGroup bossGroup;
    private final EventLoopGroup workerGroup;
    private final Integer port;
    private final WebSocketServerConfig serverConfig;
    private final WebSocketService webSocketService;
    private final MessageHandlerManager messageHandlerManager;
    private final RedisPublisher redisPublisher;
    
    public WebSocketServer(WebSocketService webSocketService, WebSocketServerConfig serverConfig, MessageHandlerManager messageHandlerManager, RedisPublisher redisPublisher) {
        Integer bossGroup = ObjectUtils.defaultIfNull(serverConfig.getBossGroup(), 1);
        Integer workGroup = ObjectUtils.defaultIfNull(serverConfig.getWorkGroup(), NettyRuntime.availableProcessors());
        Integer port = ObjectUtils.defaultIfNull(serverConfig.getPort(), 9501);
        this.bossGroup = new NioEventLoopGroup(bossGroup);
        this.workerGroup = new NioEventLoopGroup(workGroup);
        this.port = port;
        this.webSocketService = webSocketService;
        this.serverConfig = serverConfig;
        this.messageHandlerManager = messageHandlerManager;
        this.redisPublisher = redisPublisher;
    }
    
    @PostConstruct
    public void start() throws InterruptedException {
        startServer();
    }
    
    @PreDestroy
    public void destroy() {
        Future<?> future = bossGroup.shutdownGracefully();
        Future<?> future1 = workerGroup.shutdownGracefully();
        future.syncUninterruptibly();
        future1.syncUninterruptibly();
        log.info("Shutdown websocket server successfully");
    }
    
    private void startServer() throws InterruptedException {
        
        Integer aggregatorMaxContentLength = ObjectUtils.defaultIfNull(serverConfig.getAggregatorMaxContentLength(), 8192);
        Integer receiveTimeoutSeconds = ObjectUtils.defaultIfNull(serverConfig.getReceiveTimeoutSeconds(), 60);
        Integer sendTimeoutSeconds = ObjectUtils.defaultIfNull(serverConfig.getSendTimeoutSeconds(), 0);
        Integer allTimeoutSeconds = ObjectUtils.defaultIfNull(serverConfig.getAllTimeoutSeconds(), 0);
        
        // Sharable handler
        
        WebSocketServerHandler webSocketServerHandler = new WebSocketServerHandler(webSocketService, messageHandlerManager, redisPublisher);
        QuitHandler quitHandler = new QuitHandler();
        ClientIdleListenHandler clientIdleListenHandler = new ClientIdleListenHandler(receiveTimeoutSeconds, sendTimeoutSeconds, allTimeoutSeconds);
        
        // Create ServerBootStrap
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 128)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(new HttpServerCodec());
                        pipeline.addLast(new ChunkedWriteHandler());
                        pipeline.addLast(new HttpObjectAggregator(aggregatorMaxContentLength));
                        // 初始属性绑定
                        pipeline.addLast(new BindInitAttrHandler());
                        // WS 协议处理器: 如果不配置 checkStartWith，就需要在前继节中将 request.uri() 重置为 websocketPath 的相等值才会被该 WS 协议处理器处理
                        //               具体逻辑在源码: io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandshakeHandler#isWebSocketPath
                        pipeline.addLast(new WebSocketServerProtocolHandler("/", true));
                        // 探活
                        pipeline.addLast(clientIdleListenHandler.createNettyIdleStateHandler());
                        pipeline.addLast(clientIdleListenHandler);
                        // 断连
                        pipeline.addLast(quitHandler);
                        // WebSocket 消息处理
                        pipeline.addLast(webSocketServerHandler);
                    }
                });
        serverBootstrap.bind(port).sync();
    }
}
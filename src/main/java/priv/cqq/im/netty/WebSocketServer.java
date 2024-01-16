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
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;
import priv.cqq.im.netty.handler.BindInitAttrHandler;
import priv.cqq.im.netty.handler.QuitHandler;
import priv.cqq.im.netty.handler.ServerIdleStateHandler;
import priv.cqq.im.netty.handler.WebSocketServerHandler;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * WebSocket server
 *
 * @author CongQingquan
 */
@Slf4j
@Component
public class WebSocketServer {
    
    // 前端测试网站：https://wstool.js.org/
    
    // 创建线程池执行器
    private final EventLoopGroup bossGroup;
    private final EventLoopGroup workerGroup;
    
    private final Integer port;
    
    private final Integer aggregatorMaxContentLength;
    
    public WebSocketServer(WebSocketServerConfig serverConfig) {
        Integer bossGroup = ObjectUtils.defaultIfNull(serverConfig.getBossGroup(), 1);
        Integer workGroup = ObjectUtils.defaultIfNull(serverConfig.getWorkGroup(), NettyRuntime.availableProcessors());
        Integer port = ObjectUtils.defaultIfNull(serverConfig.getPort(), 9501);
        Integer aggregatorMaxContentLength = ObjectUtils.defaultIfNull(serverConfig.getAggregatorMaxContentLength(), 8192);
        this.bossGroup = new NioEventLoopGroup(bossGroup);
        this.workerGroup = new NioEventLoopGroup(workGroup);
        this.port = port;
        this.aggregatorMaxContentLength = aggregatorMaxContentLength;
    }
    
    @PostConstruct
    public void start() throws InterruptedException {
        startServer();
    }
    
    /**
     * 销毁
     */
    @PreDestroy
    public void destroy() {
        Future<?> future = bossGroup.shutdownGracefully();
        Future<?> future1 = workerGroup.shutdownGracefully();
        future.syncUninterruptibly();
        future1.syncUninterruptibly();
        log.info("关闭 ws server 成功");
    }
    
    private void startServer() throws InterruptedException {
        // Sharable handler
        WebSocketServerHandler webSocketServerHandler = new WebSocketServerHandler();
        QuitHandler quitHandler = new QuitHandler();
        ServerIdleStateHandler serverIdleStateHandler = new ServerIdleStateHandler();
        
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
                        // 属性绑定
                        pipeline.addLast(new BindInitAttrHandler());
                        pipeline.addLast(new WebSocketServerProtocolHandler("/"));
                        // WebSocket 消息处理
                        pipeline.addLast(webSocketServerHandler);
                        // 探活
                        pipeline.addLast(ServerIdleStateHandler.createNettyServerIdleStateHandler());
                        pipeline.addLast(serverIdleStateHandler);
                        // 断连
                        pipeline.addLast(quitHandler);
                    }
                });
        serverBootstrap.bind(port).sync();
    }
}
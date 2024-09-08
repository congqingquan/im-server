package priv.cqq.im.netty.constants;

import io.netty.util.AttributeKey;

/**
 * Netty constants
 *
 * @author Qingquan.Cong
 */
public class NettyConstants {

    public static AttributeKey<String> TOKEN = AttributeKey.valueOf("token");

    public static AttributeKey<String> IP = AttributeKey.valueOf("ip");

    public static AttributeKey<Long> UID = AttributeKey.valueOf("uid");
    
    public static final String DISTRIBUTED_IM_MESSAGE_TOPIC = "DISTRIBUTED_IM_MESSAGE_TOPIC";
}
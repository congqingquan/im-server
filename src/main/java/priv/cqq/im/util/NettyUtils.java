package priv.cqq.im.util;

import io.netty.channel.Channel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

/**
 * Netty utils
 *
 * @author Qingquan.Cong
 */
public class NettyUtils {

    public static <T> void setAttr(Channel channel, AttributeKey<T> attributeKey, T data) {
        Attribute<T> attr = channel.attr(attributeKey);
        attr.set(data);
    }

    public static <T> T getAttr(Channel channel, AttributeKey<T> attributeKey) {
        // Avoid NPL, `channel.attr(attributeKey)` will return DefaultAttribute instead of null.
        return channel.attr(attributeKey).get();
    }
}

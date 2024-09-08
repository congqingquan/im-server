package priv.cqq.im.netty.entity.message;

import lombok.Data;
import priv.cqq.im.netty.enums.MessageCategoryEnum;

/**
 * Message
 *
 * @author CongQingquan
 */
@Data
public class Message {
    
    private Long fromUserId;
    
    private Long targetUserId;
    
    /**
     * 消费标记: 用于处理接受某次请求后，使用 targetId
     */
    private boolean consumed;
    
    /**
     * @see MessageCategoryEnum
     */
    private String category;
}

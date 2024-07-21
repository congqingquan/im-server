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
    
    /**
     * @see MessageCategoryEnum
     */
    private String category;
}

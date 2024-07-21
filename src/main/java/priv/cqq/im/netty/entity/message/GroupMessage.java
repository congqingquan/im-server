package priv.cqq.im.netty.entity.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Group message entity
 *
 * @author CongQingquan
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class GroupMessage extends Message {
    
    private Long fromUserId;
    
    private Long chatGroupId;
    
    private String content;
    
    /**
     * @see priv.cqq.im.netty.enums.MessageTypeEnum
     */
    private String type;
}

package priv.cqq.im.netty.entity.message;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Direct message entity
 *
 * @author CongQingquan
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DirectMessage extends Message {
    
    private Long from;
    
    private Long target;
    
    private String content;
    
    private Integer DMSType;
}

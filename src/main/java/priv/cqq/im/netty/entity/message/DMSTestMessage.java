package priv.cqq.im.netty.entity.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * DMS test message entity
 *
 * {
 *   "category": "DMS_TEST",
 *   "fromUserId": 1,
 *   "targetUserId": 2,
 *   "content": "Hello Cqq2"
 * }
 *
 * @author CongQingquan
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class DMSTestMessage extends Message {
    
    private String content;
}

package priv.cqq.im.netty.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Message category enum
 *
 * @author CongQingquan
 */
@Getter
@AllArgsConstructor
public enum MessageCategoryEnum {
    
    DMS_TEST,
    GROUP,
    HEARTBEAT
}
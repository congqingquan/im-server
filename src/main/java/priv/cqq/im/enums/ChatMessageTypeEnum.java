package priv.cqq.im.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Chat message type enum
 *
 * @author CongQingquan
 */
@Getter
@AllArgsConstructor
public enum ChatMessageTypeEnum {
    
    TEXT(1),
    REPLY(2),
    ;
    
    private final Integer type;
}
package priv.cqq.im.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Chat message target type enum
 *
 * @author CongQingquan
 */
@Getter
@AllArgsConstructor
public enum ChatMessageTargetTypeEnum {
    
    USER(1),
    GROUP(2),
    ;
    
    private final Integer type;
}
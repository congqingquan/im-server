package priv.cqq.im.netty.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 群组成员类型枚举
 *
 * @author CongQingquan
 */
@Getter
@AllArgsConstructor
public enum GroupMemberTypeEnum {
    
    OWNER,
    ADMIN,
    MEMBER
}

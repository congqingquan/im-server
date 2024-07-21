package priv.cqq.im.manager;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.cqq.openlibrary.common.util.ArrayUtils;
import org.cqq.openlibrary.common.util.ReturnableBeanUtils;
import org.springframework.stereotype.Service;
import priv.cqq.im.dao.IMChatGroupMemberMapper;
import priv.cqq.im.domain.dto.CImChatGroupMemberPageDTO;
import priv.cqq.im.domain.po.IMChatGroupMember;
import priv.cqq.im.domain.vo.CImChatGroupMemberPageVO;
import priv.cqq.im.domain.vo.CImChatGroupMemberViewVO;

import java.util.List;

@Service
@AllArgsConstructor
public class IMChatGroupMemberManager extends ServiceImpl<IMChatGroupMemberMapper, IMChatGroupMember> {

    public IPage<CImChatGroupMemberPageVO> page(IPage<IMChatGroupMember> pageParam, CImChatGroupMemberPageDTO pageDTO) {
        return null;
    }

    public CImChatGroupMemberViewVO view(Long groupMemberId) {
        IMChatGroupMember imChatGroupMember = getById(groupMemberId);
        if (imChatGroupMember == null) {
            return null;
        }
        return ReturnableBeanUtils.copyProperties(imChatGroupMember, new CImChatGroupMemberViewVO());
    }
    
    public List<IMChatGroupMember> selectMembersByGroupId(Long groupId, Long... excludeGroupMemberUserIds) {
        return lambdaQuery()
                .eq(IMChatGroupMember::getGroupId, groupId)
                .notIn(ArrayUtils.isNotEmpty(excludeGroupMemberUserIds), IMChatGroupMember::getUserId, List.of(excludeGroupMemberUserIds))
                .list();
    }
}
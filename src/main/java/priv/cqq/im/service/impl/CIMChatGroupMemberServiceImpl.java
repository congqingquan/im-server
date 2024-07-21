package priv.cqq.im.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import org.cqq.openlibrary.common.util.ReturnableBeanUtils;
import org.springframework.stereotype.Service;
import priv.cqq.im.domain.dto.CImChatGroupMemberAddDTO;
import priv.cqq.im.domain.dto.CImChatGroupMemberPageDTO;
import priv.cqq.im.domain.dto.CImChatGroupMemberUpdateDTO;
import priv.cqq.im.domain.po.IMChatGroupMember;
import priv.cqq.im.domain.vo.CImChatGroupMemberPageVO;
import priv.cqq.im.domain.vo.CImChatGroupMemberViewVO;
import priv.cqq.im.manager.IMChatGroupMemberManager;
import priv.cqq.im.service.CIMChatGroupMemberService;

import java.util.List;

/**
 * IM群成员表 Service 实现
 *
 * @author CongQingquan
 */
@Service
@AllArgsConstructor
public class CIMChatGroupMemberServiceImpl implements CIMChatGroupMemberService {

    private final IMChatGroupMemberManager imChatGroupMemberManager;

    @Override
    public IPage<CImChatGroupMemberPageVO> page(IPage<IMChatGroupMember> pageParam, CImChatGroupMemberPageDTO pageDTO) {
        return imChatGroupMemberManager.page(pageParam, pageDTO);
    }

    @Override
    public CImChatGroupMemberViewVO view(Long groupMemberId) {
        return imChatGroupMemberManager.view(groupMemberId);
    }

    @Override
    public Boolean add(CImChatGroupMemberAddDTO addDTO) {
        return imChatGroupMemberManager.save(ReturnableBeanUtils.copyProperties(addDTO, new IMChatGroupMember()));
    }

    @Override
    public Boolean edit(CImChatGroupMemberUpdateDTO updateDTO) {
        return imChatGroupMemberManager.updateById(ReturnableBeanUtils.copyProperties(updateDTO, new IMChatGroupMember()));
    }

    @Override
    public Boolean delete(List<Long> groupMemberIdList) {
        return imChatGroupMemberManager.removeBatchByIds(groupMemberIdList);
    }
}
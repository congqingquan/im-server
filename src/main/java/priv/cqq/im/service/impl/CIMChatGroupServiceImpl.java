package priv.cqq.im.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import org.cqq.openlibrary.common.util.ReturnableBeanUtils;
import org.springframework.stereotype.Service;
import priv.cqq.im.domain.dto.CImChatGroupAddDTO;
import priv.cqq.im.domain.dto.CImChatGroupPageDTO;
import priv.cqq.im.domain.dto.CImChatGroupUpdateDTO;
import priv.cqq.im.domain.po.IMChatGroup;
import priv.cqq.im.domain.vo.CImChatGroupPageVO;
import priv.cqq.im.domain.vo.CImChatGroupViewVO;
import priv.cqq.im.manager.IMChatGroupManager;
import priv.cqq.im.service.CIMChatGroupService;

import java.util.List;

/**
 * IM群聊表 Service 实现
 *
 * @author CongQingquan
 */
@Service
@AllArgsConstructor
public class CIMChatGroupServiceImpl implements CIMChatGroupService {

    private final IMChatGroupManager imChatGroupManager;

    @Override
    public IPage<CImChatGroupPageVO> page(IPage<IMChatGroup> pageParam, CImChatGroupPageDTO pageDTO) {
        return imChatGroupManager.page(pageParam, pageDTO);
    }

    @Override
    public CImChatGroupViewVO view(Long groupId) {
        return imChatGroupManager.view(groupId);
    }

    @Override
    public Boolean add(CImChatGroupAddDTO addDTO) {
        return imChatGroupManager.save(ReturnableBeanUtils.copyProperties(addDTO, new IMChatGroup()));
    }

    @Override
    public Boolean edit(CImChatGroupUpdateDTO updateDTO) {
        return imChatGroupManager.updateById(ReturnableBeanUtils.copyProperties(updateDTO, new IMChatGroup()));
    }

    @Override
    public Boolean delete(List<Long> groupIdList) {
        return imChatGroupManager.removeBatchByIds(groupIdList);
    }
}
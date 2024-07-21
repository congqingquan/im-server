package priv.cqq.im.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import org.cqq.openlibrary.common.util.ReturnableBeanUtils;
import org.springframework.stereotype.Service;
import priv.cqq.im.domain.dto.CImChatSessionAddDTO;
import priv.cqq.im.domain.dto.CImChatSessionPageDTO;
import priv.cqq.im.domain.dto.CImChatSessionUpdateDTO;
import priv.cqq.im.domain.po.IMChatSession;
import priv.cqq.im.domain.vo.CImChatSessionPageVO;
import priv.cqq.im.domain.vo.CImChatSessionViewVO;
import priv.cqq.im.manager.IMChatSessionManager;
import priv.cqq.im.service.CIMChatSessionService;

import java.util.List;

/**
 * IM聊天会话列表 Service 实现
 *
 * @author CongQingquan
 */
@Service
@AllArgsConstructor
public class CIMChatSessionServiceImpl implements CIMChatSessionService {

    private final IMChatSessionManager imChatSessionManager;

    @Override
    public IPage<CImChatSessionPageVO> page(IPage<IMChatSession> pageParam, CImChatSessionPageDTO pageDTO) {
        return imChatSessionManager.page(pageParam, pageDTO);
    }

    @Override
    public CImChatSessionViewVO view(Long sessionId) {
        return imChatSessionManager.view(sessionId);
    }

    @Override
    public Boolean add(CImChatSessionAddDTO addDTO) {
        return imChatSessionManager.save(ReturnableBeanUtils.copyProperties(addDTO, new IMChatSession()));
    }

    @Override
    public Boolean edit(CImChatSessionUpdateDTO updateDTO) {
        return imChatSessionManager.updateById(ReturnableBeanUtils.copyProperties(updateDTO, new IMChatSession()));
    }

    @Override
    public Boolean delete(List<Long> sessionIdList) {
        return imChatSessionManager.removeBatchByIds(sessionIdList);
    }
}
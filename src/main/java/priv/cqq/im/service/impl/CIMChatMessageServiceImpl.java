package priv.cqq.im.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import org.cqq.openlibrary.common.util.ReturnableBeanUtils;
import org.springframework.stereotype.Service;
import priv.cqq.im.domain.dto.CIMChatMessageAddDTO;
import priv.cqq.im.domain.dto.CIMChatMessagePageDTO;
import priv.cqq.im.domain.dto.CIMChatMessageUpdateDTO;
import priv.cqq.im.domain.po.IMChatMessage;
import priv.cqq.im.domain.vo.CImChatMessagePageVO;
import priv.cqq.im.domain.vo.CImChatMessageViewVO;
import priv.cqq.im.manager.IMChatMessageManager;
import priv.cqq.im.service.CIMChatMessageService;

import java.util.List;

/**
 * IM消息表 Service 实现
 *
 * @author CongQingquan
 */
@Service
@AllArgsConstructor
public class CIMChatMessageServiceImpl implements CIMChatMessageService {

    private final IMChatMessageManager imChatMessageManager;

    @Override
    public IPage<CImChatMessagePageVO> page(IPage<IMChatMessage> pageParam, CIMChatMessagePageDTO pageDTO) {
        return imChatMessageManager.page(pageParam, pageDTO);
    }

    @Override
    public CImChatMessageViewVO view(Long messageId) {
        return imChatMessageManager.view(messageId);
    }

    @Override
    public Boolean add(CIMChatMessageAddDTO addDTO) {
        return imChatMessageManager.save(ReturnableBeanUtils.copyProperties(addDTO, new IMChatMessage()));
    }

    @Override
    public Boolean edit(CIMChatMessageUpdateDTO updateDTO) {
        return imChatMessageManager.updateById(ReturnableBeanUtils.copyProperties(updateDTO, new IMChatMessage()));
    }

    @Override
    public Boolean delete(List<Long> messageIdList) {
        return imChatMessageManager.removeBatchByIds(messageIdList);
    }
}
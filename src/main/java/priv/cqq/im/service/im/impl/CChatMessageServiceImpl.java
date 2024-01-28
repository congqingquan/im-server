package priv.cqq.im.service.im.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import org.cqq.openlibrary.core.util.BeanUtils;
import org.springframework.stereotype.Service;
import priv.cqq.im.domain.dto.CChatMessageAddDTO;
import priv.cqq.im.domain.dto.CChatMessagePageDTO;
import priv.cqq.im.domain.dto.CChatMessageUpdateDTO;
import priv.cqq.im.domain.po.ChatMessage;
import priv.cqq.im.domain.vo.CChatMessagePageVO;
import priv.cqq.im.domain.vo.CChatMessageViewVO;
import priv.cqq.im.manager.ChatMessageManager;
import priv.cqq.im.service.im.CChatMessageService;

/**
 * IM消息表 Service 实现
 *
 * @author CongQingquan
 */
@Service
@AllArgsConstructor
public class CChatMessageServiceImpl implements CChatMessageService {

    private final ChatMessageManager chatMessageManager;

    @Override
    public IPage<CChatMessagePageVO> page(IPage<ChatMessage> pageParam, CChatMessagePageDTO pageDTO) {
        return chatMessageManager.page(pageParam, pageDTO);
    }

    @Override
    public CChatMessageViewVO view(Long messageId) {
        return chatMessageManager.view(messageId);
    }

    @Override
    public Boolean add(CChatMessageAddDTO addDTO) {
        return chatMessageManager.save(BeanUtils.copy(addDTO, new ChatMessage()));
    }

    @Override
    public Boolean edit(CChatMessageUpdateDTO updateDTO) {
        return chatMessageManager.updateById(BeanUtils.copy(updateDTO, new ChatMessage()));
    }
}
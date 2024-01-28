package priv.cqq.im.manager;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.cqq.openlibrary.core.util.BeanUtils;
import org.springframework.stereotype.Service;
import priv.cqq.im.dao.ChatMessageMapper;
import priv.cqq.im.domain.dto.CChatMessagePageDTO;
import priv.cqq.im.domain.po.ChatMessage;
import priv.cqq.im.domain.vo.CChatMessagePageVO;
import priv.cqq.im.domain.vo.CChatMessageViewVO;

/**
 * IM消息表 Manager
 *
 * @author CongQingquan
 */
@Service
@AllArgsConstructor
public class ChatMessageManager extends ServiceImpl<ChatMessageMapper, ChatMessage> {

    public IPage<CChatMessagePageVO> page(IPage<ChatMessage> pageParam, CChatMessagePageDTO pageDTO) {
        return null;
    }

    public CChatMessageViewVO view(Long messageId) {
        ChatMessage chatMessage = getById(messageId);
        if (chatMessage == null) {
            return null;
        }
        return BeanUtils.copy(chatMessage, new CChatMessageViewVO());
    }
}
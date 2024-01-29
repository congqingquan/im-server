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
import priv.cqq.im.enums.ChatMessageTargetTypeEnum;
import priv.cqq.im.netty.entity.message.DirectMessage;
import priv.cqq.im.util.ChatMessageUtils;

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
    
    public void saveDirectMessage(DirectMessage message) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setFromUserId(message.getFrom());
        chatMessage.setTargetType(ChatMessageTargetTypeEnum.USER.getType());
        chatMessage.setTargetUserId(message.getTarget());
        chatMessage.setFromTargetUserKey(ChatMessageUtils.genFromTargetUserKey(message.getFrom(), message.getTarget()));
        chatMessage.setContent(message.getContent());
        chatMessage.setType(message.getDMSType());
        save(chatMessage);
    }
}
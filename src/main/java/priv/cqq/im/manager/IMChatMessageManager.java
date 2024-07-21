package priv.cqq.im.manager;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.cqq.openlibrary.common.util.ReturnableBeanUtils;
import org.springframework.stereotype.Service;
import priv.cqq.im.dao.IMChatMessageMapper;
import priv.cqq.im.domain.dto.CIMChatMessagePageDTO;
import priv.cqq.im.domain.po.IMChatMessage;
import priv.cqq.im.domain.vo.CImChatMessagePageVO;
import priv.cqq.im.domain.vo.CImChatMessageViewVO;
import priv.cqq.im.netty.entity.message.GroupMessage;

@Service
@AllArgsConstructor
public class IMChatMessageManager extends ServiceImpl<IMChatMessageMapper, IMChatMessage> {

    public IPage<CImChatMessagePageVO> page(IPage<IMChatMessage> pageParam, CIMChatMessagePageDTO pageDTO) {
        return null;
    }

    public CImChatMessageViewVO view(Long messageId) {
        IMChatMessage imChatMessage = getById(messageId);
        if (imChatMessage == null) {
            return null;
        }
        return ReturnableBeanUtils.copyProperties(imChatMessage, new CImChatMessageViewVO());
    }
    
    public void saveGroupMessage(GroupMessage message) {
        IMChatMessage chatMessage = new IMChatMessage();
        chatMessage.setFromUserId(message.getFromUserId());
        chatMessage.setChatGroupId(message.getChatGroupId());
        chatMessage.setContent(message.getContent());
        // 暂不处理回复消息
        // chatMessage.setReplyMsgId();
        // chatMessage.setGapCount();
        chatMessage.setType(message.getType());
        save(chatMessage);
    }
}
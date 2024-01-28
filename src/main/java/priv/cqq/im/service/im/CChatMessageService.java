package priv.cqq.im.service.im;

import com.baomidou.mybatisplus.core.metadata.IPage;
import priv.cqq.im.domain.dto.CChatMessageAddDTO;
import priv.cqq.im.domain.dto.CChatMessagePageDTO;
import priv.cqq.im.domain.dto.CChatMessageUpdateDTO;
import priv.cqq.im.domain.po.ChatMessage;
import priv.cqq.im.domain.vo.CChatMessagePageVO;
import priv.cqq.im.domain.vo.CChatMessageViewVO;

/**
 * IM消息表 Service
 *
 * @author CongQingquan
 */
public interface CChatMessageService {

    /**
     * 分页
     */
    IPage<CChatMessagePageVO> page(IPage<ChatMessage> pageParam, CChatMessagePageDTO pageDTO);

    /**
    * 详情
    */
    CChatMessageViewVO view(Long messageId);

    /**
    * 新增
    */
    Boolean add(CChatMessageAddDTO addDTO);

    /**
    * 编辑
    */
    Boolean edit(CChatMessageUpdateDTO updateDTO);
}
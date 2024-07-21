package priv.cqq.im.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import priv.cqq.im.domain.po.IMChatMessage;
import priv.cqq.im.domain.vo.CImChatMessagePageVO;
import priv.cqq.im.domain.vo.CImChatMessageViewVO;
import priv.cqq.im.domain.dto.CIMChatMessagePageDTO;
import priv.cqq.im.domain.dto.CIMChatMessageAddDTO;
import priv.cqq.im.domain.dto.CIMChatMessageUpdateDTO;
import java.util.List;

/**
 * IM消息表 Service
 *
 * @author CongQingquan
 */
public interface CIMChatMessageService {

    /**
     * 分页
     */
    IPage<CImChatMessagePageVO> page(IPage<IMChatMessage> pageParam, CIMChatMessagePageDTO pageDTO);

    /**
    * 详情
    */
    CImChatMessageViewVO view(Long messageId);

    /**
    * 新增
    */
    Boolean add(CIMChatMessageAddDTO addDTO);

    /**
    * 编辑
    */
    Boolean edit(CIMChatMessageUpdateDTO updateDTO);

    /**
    * 删除
    */
    Boolean delete(List<Long> messageIdList);
}
package priv.cqq.im.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import priv.cqq.im.domain.po.IMChatSession;
import priv.cqq.im.domain.vo.CImChatSessionPageVO;
import priv.cqq.im.domain.vo.CImChatSessionViewVO;
import priv.cqq.im.domain.dto.CImChatSessionPageDTO;
import priv.cqq.im.domain.dto.CImChatSessionAddDTO;
import priv.cqq.im.domain.dto.CImChatSessionUpdateDTO;
import java.util.List;

/**
 * IM聊天会话列表 Service
 *
 * @author CongQingquan
 */
public interface CIMChatSessionService {

    /**
     * 分页
     */
    IPage<CImChatSessionPageVO> page(IPage<IMChatSession> pageParam, CImChatSessionPageDTO pageDTO);

    /**
    * 详情
    */
    CImChatSessionViewVO view(Long sessionId);

    /**
    * 新增
    */
    Boolean add(CImChatSessionAddDTO addDTO);

    /**
    * 编辑
    */
    Boolean edit(CImChatSessionUpdateDTO updateDTO);

    /**
    * 删除
    */
    Boolean delete(List<Long> sessionIdList);
}
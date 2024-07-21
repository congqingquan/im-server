package priv.cqq.im.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import priv.cqq.im.domain.po.IMChatGroup;
import priv.cqq.im.domain.vo.CImChatGroupPageVO;
import priv.cqq.im.domain.vo.CImChatGroupViewVO;
import priv.cqq.im.domain.dto.CImChatGroupPageDTO;
import priv.cqq.im.domain.dto.CImChatGroupAddDTO;
import priv.cqq.im.domain.dto.CImChatGroupUpdateDTO;
import java.util.List;

/**
 * IM群聊表 Service
 *
 * @author CongQingquan
 */
public interface CIMChatGroupService {

    /**
     * 分页
     */
    IPage<CImChatGroupPageVO> page(IPage<IMChatGroup> pageParam, CImChatGroupPageDTO pageDTO);

    /**
    * 详情
    */
    CImChatGroupViewVO view(Long groupId);

    /**
    * 新增
    */
    Boolean add(CImChatGroupAddDTO addDTO);

    /**
    * 编辑
    */
    Boolean edit(CImChatGroupUpdateDTO updateDTO);

    /**
    * 删除
    */
    Boolean delete(List<Long> groupIdList);
}
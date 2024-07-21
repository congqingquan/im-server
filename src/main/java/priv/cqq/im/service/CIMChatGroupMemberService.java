package priv.cqq.im.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import priv.cqq.im.domain.po.IMChatGroupMember;
import priv.cqq.im.domain.vo.CImChatGroupMemberPageVO;
import priv.cqq.im.domain.vo.CImChatGroupMemberViewVO;
import priv.cqq.im.domain.dto.CImChatGroupMemberPageDTO;
import priv.cqq.im.domain.dto.CImChatGroupMemberAddDTO;
import priv.cqq.im.domain.dto.CImChatGroupMemberUpdateDTO;
import java.util.List;

/**
 * IM群成员表 Service
 *
 * @author CongQingquan
 */
public interface CIMChatGroupMemberService {

    /**
     * 分页
     */
    IPage<CImChatGroupMemberPageVO> page(IPage<IMChatGroupMember> pageParam, CImChatGroupMemberPageDTO pageDTO);

    /**
    * 详情
    */
    CImChatGroupMemberViewVO view(Long groupMemberId);

    /**
    * 新增
    */
    Boolean add(CImChatGroupMemberAddDTO addDTO);

    /**
    * 编辑
    */
    Boolean edit(CImChatGroupMemberUpdateDTO updateDTO);

    /**
    * 删除
    */
    Boolean delete(List<Long> groupMemberIdList);
}
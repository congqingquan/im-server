package priv.cqq.im.manager;

import lombok.AllArgsConstructor;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import priv.cqq.im.dao.IMChatGroupMapper;
import priv.cqq.im.domain.po.IMChatGroup;
import priv.cqq.im.domain.vo.CImChatGroupPageVO;
import priv.cqq.im.domain.vo.CImChatGroupViewVO;
import priv.cqq.im.domain.dto.CImChatGroupPageDTO;
import org.cqq.openlibrary.common.util.ReturnableBeanUtils;

@Service
@AllArgsConstructor
public class IMChatGroupManager extends ServiceImpl<IMChatGroupMapper, IMChatGroup> {

    public IPage<CImChatGroupPageVO> page(IPage<IMChatGroup> pageParam, CImChatGroupPageDTO pageDTO) {
        return null;
    }

    public CImChatGroupViewVO view(Long groupId) {
        IMChatGroup imChatGroup = getById(groupId);
        if (imChatGroup == null) {
            return null;
        }
        return ReturnableBeanUtils.copyProperties(imChatGroup, new CImChatGroupViewVO());
    }
}
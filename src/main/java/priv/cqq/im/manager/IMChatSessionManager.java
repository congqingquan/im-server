package priv.cqq.im.manager;

import lombok.AllArgsConstructor;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import priv.cqq.im.dao.IMChatSessionMapper;
import priv.cqq.im.domain.po.IMChatSession;
import priv.cqq.im.domain.vo.CImChatSessionPageVO;
import priv.cqq.im.domain.vo.CImChatSessionViewVO;
import priv.cqq.im.domain.dto.CImChatSessionPageDTO;
import org.cqq.openlibrary.common.util.ReturnableBeanUtils;

@Service
@AllArgsConstructor
public class IMChatSessionManager extends ServiceImpl<IMChatSessionMapper, IMChatSession> {

    public IPage<CImChatSessionPageVO> page(IPage<IMChatSession> pageParam, CImChatSessionPageDTO pageDTO) {
        return null;
    }

    public CImChatSessionViewVO view(Long sessionId) {
        IMChatSession imChatSession = getById(sessionId);
        if (imChatSession == null) {
            return null;
        }
        return ReturnableBeanUtils.copyProperties(imChatSession, new CImChatSessionViewVO());
    }
}
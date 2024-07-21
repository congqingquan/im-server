package priv.cqq.im.manager;

import lombok.AllArgsConstructor;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import priv.cqq.im.dao.IMUserContactMapper;
import priv.cqq.im.domain.po.IMUserContact;
import priv.cqq.im.domain.vo.CImUserContactPageVO;
import priv.cqq.im.domain.vo.CImUserContactViewVO;
import priv.cqq.im.domain.dto.CImUserContactPageDTO;
import org.cqq.openlibrary.common.util.ReturnableBeanUtils;

@Service
@AllArgsConstructor
public class IMUserContactManager extends ServiceImpl<IMUserContactMapper, IMUserContact> {

    public IPage<CImUserContactPageVO> page(IPage<IMUserContact> pageParam, CImUserContactPageDTO pageDTO) {
        return null;
    }

    public CImUserContactViewVO view(Long concatId) {
        IMUserContact imUserContact = getById(concatId);
        if (imUserContact == null) {
            return null;
        }
        return ReturnableBeanUtils.copyProperties(imUserContact, new CImUserContactViewVO());
    }
}
package priv.cqq.im.service.impl;

import lombok.AllArgsConstructor;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.stereotype.Service;
import priv.cqq.im.manager.IMUserContactManager;
import priv.cqq.im.domain.po.IMUserContact;
import priv.cqq.im.service.CIMUserContactService;
import priv.cqq.im.domain.vo.CImUserContactPageVO;
import priv.cqq.im.domain.vo.CImUserContactViewVO;
import priv.cqq.im.domain.dto.CImUserContactPageDTO;
import priv.cqq.im.domain.dto.CImUserContactAddDTO;
import priv.cqq.im.domain.dto.CImUserContactUpdateDTO;
import org.cqq.openlibrary.common.constants.Constants;
import org.cqq.openlibrary.common.util.ReturnableBeanUtils;
import java.util.List;
import java.util.ArrayList;

/**
 * IM用户联系人表 Service 实现
 *
 * @author CongQingquan
 */
@Service
@AllArgsConstructor
public class CIMUserContactServiceImpl implements CIMUserContactService {

    private final IMUserContactManager imUserContactManager;

    @Override
    public IPage<CImUserContactPageVO> page(IPage<IMUserContact> pageParam, CImUserContactPageDTO pageDTO) {
        return imUserContactManager.page(pageParam, pageDTO);
    }

    @Override
    public CImUserContactViewVO view(Long concatId) {
        return imUserContactManager.view(concatId);
    }

    @Override
    public Boolean add(CImUserContactAddDTO addDTO) {
        return imUserContactManager.save(ReturnableBeanUtils.copyProperties(addDTO, new IMUserContact()));
    }

    @Override
    public Boolean edit(CImUserContactUpdateDTO updateDTO) {
        return imUserContactManager.updateById(ReturnableBeanUtils.copyProperties(updateDTO, new IMUserContact()));
    }

    @Override
    public Boolean delete(List<Long> concatIdList) {
        List<IMUserContact> param = new ArrayList<>();
        concatIdList.forEach(concatId -> param.add(new IMUserContact().setConcatId(concatId).setDeleted(Constants.DELETED)));
        return imUserContactManager.updateBatchById(param, param.size());
    }
}
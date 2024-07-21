package priv.cqq.im.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import priv.cqq.im.domain.po.IMUserContact;
import priv.cqq.im.domain.vo.CImUserContactPageVO;
import priv.cqq.im.domain.vo.CImUserContactViewVO;
import priv.cqq.im.domain.dto.CImUserContactPageDTO;
import priv.cqq.im.domain.dto.CImUserContactAddDTO;
import priv.cqq.im.domain.dto.CImUserContactUpdateDTO;
import java.util.List;

/**
 * IM用户联系人表 Service
 *
 * @author CongQingquan
 */
public interface CIMUserContactService {

    /**
     * 分页
     */
    IPage<CImUserContactPageVO> page(IPage<IMUserContact> pageParam, CImUserContactPageDTO pageDTO);

    /**
    * 详情
    */
    CImUserContactViewVO view(Long concatId);

    /**
    * 新增
    */
    Boolean add(CImUserContactAddDTO addDTO);

    /**
    * 编辑
    */
    Boolean edit(CImUserContactUpdateDTO updateDTO);

    /**
    * 删除
    */
    Boolean delete(List<Long> concatIdList);
}
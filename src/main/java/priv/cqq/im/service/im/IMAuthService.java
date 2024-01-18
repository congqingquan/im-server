package priv.cqq.im.service.im;

import priv.cqq.im.domain.dto.IMUserLoginDTO;
import priv.cqq.im.domain.vo.IMUserLoginVO;

/**
 * IM Auth service
 *
 * @author Qingquan.Cong
 */
public interface IMAuthService {

    IMUserLoginVO login(IMUserLoginDTO loginDTO);
}
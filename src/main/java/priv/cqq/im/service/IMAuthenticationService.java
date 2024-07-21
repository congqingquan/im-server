package priv.cqq.im.service;

import priv.cqq.im.domain.dto.IMUserLoginDTO;
import priv.cqq.im.domain.vo.IMUserLoginVO;

/**
 * IM Auth service
 *
 * @author Qingquan.Cong
 */
public interface IMAuthenticationService {

    IMUserLoginVO login(IMUserLoginDTO loginDTO);
}
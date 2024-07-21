package priv.cqq.im.service.impl;

import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import org.cqq.openlibrary.common.exception.BusinessException;
import org.cqq.openlibrary.common.util.JSONUtils;
import org.cqq.openlibrary.common.util.JWSUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import priv.cqq.im.config.JWSAuthenticationConfig;
import priv.cqq.im.domain.dto.IMUserAuthDTO;
import priv.cqq.im.domain.dto.IMUserLoginDTO;
import priv.cqq.im.domain.po.IMUser;
import priv.cqq.im.domain.vo.IMUserLoginVO;
import priv.cqq.im.manager.IMUserManager;
import priv.cqq.im.service.IMAuthenticationService;

import java.util.HashMap;
import java.util.Map;

/**
 * IM Auth service impl
 *
 * @author Qingquan.Cong
 */
@Service
@AllArgsConstructor
public class IMAuthenticationServiceImpl implements IMAuthenticationService {

    private final IMUserManager userManager;

    private final JWSAuthenticationConfig jwsAuthenticationConfig;

    @Override
    public IMUserLoginVO login(IMUserLoginDTO loginDTO) {
        // 1. 验证
        IMUser user = userManager.lambdaQuery().eq(IMUser::getUsername, loginDTO.getUsername())
                .eq(IMUser::getPassword, loginDTO.getPassword()).one();
        if (user == null) {
            throw new BusinessException("用户名或密码错误");
        }

        // 2. 返回 token
        IMUserAuthDTO authDTO = new IMUserAuthDTO();
        BeanUtils.copyProperties(user, authDTO);

        Map<String, Object> payload = new HashMap<>();
        payload.put(jwsAuthenticationConfig.getUserPayloadKey(), JSONUtils.toJSONString(authDTO));
        String token = JWSUtils.sign(
                null,
                payload,
                SignatureAlgorithm.forName(jwsAuthenticationConfig.getSignatureAlgorithm()),
                jwsAuthenticationConfig.getSecretKey(),
                jwsAuthenticationConfig.getDuration()
        );

        return new IMUserLoginVO(token);
    }
}
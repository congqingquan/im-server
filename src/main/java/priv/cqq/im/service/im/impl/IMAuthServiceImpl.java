package priv.cqq.im.service.im.impl;

import cn.hutool.json.JSONUtil;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import org.cqq.oplibrary.web.exception.BusinessException;
import org.cqq.oplibrary.web.util.JWSUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import priv.cqq.im.config.JWSConfig;
import priv.cqq.im.domain.dto.IMUserAuthDTO;
import priv.cqq.im.domain.dto.IMUserLoginDTO;
import priv.cqq.im.domain.po.IMUser;
import priv.cqq.im.domain.vo.IMUserLoginVO;
import priv.cqq.im.manager.IMUserManager;
import priv.cqq.im.service.im.IMAuthService;

import java.util.HashMap;
import java.util.Map;

/**
 * IM Auth service impl
 *
 * @author Qingquan.Cong
 */
@Service
@AllArgsConstructor
public class IMAuthServiceImpl implements IMAuthService {

    private final IMUserManager userManager;

    private final JWSConfig jwsConfig;

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
        payload.put(jwsConfig.getUserPayloadKey(), JSONUtil.toJsonStr(authDTO));
        String token = JWSUtils.sign(null, payload, SignatureAlgorithm.forName(jwsConfig.getSignatureAlgorithm()), jwsConfig.getSecretKey(), jwsConfig.getDuration());

        return new IMUserLoginVO(token);
    }
}
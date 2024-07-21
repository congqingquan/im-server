package priv.cqq.im.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.cqq.openlibrary.common.exception.BusinessException;
import org.cqq.openlibrary.common.util.HttpContext;
import org.cqq.openlibrary.common.util.JSONUtils;
import org.cqq.openlibrary.common.util.JWSUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import priv.cqq.im.domain.po.IMUser;

import java.util.Optional;

/**
 * Created by Kim QQ.Cong on 2023/1/1 / 03:10
 *
 * @author: CongQingquan
 * @Description: Jws user utils
 */
@Slf4j
@Component
public class JWSUserUtils {

    private static String authHeader;

    private static String userPayloadKey;

    private static SignatureAlgorithm signatureAlgorithm;

    private static String secretKey;


    public JWSUserUtils(@Value("${auth.jws.authHeader}") String authHeader,
                        @Value("${auth.jws.userPayloadKey}") String userPayloadKey,
                        @Value("${auth.jws.signatureAlgorithm}") String signatureAlgorithm,
                        @Value("${auth.jws.secretKey}") String secretKey) {
        JWSUserUtils.authHeader = authHeader;
        JWSUserUtils.userPayloadKey = userPayloadKey;
        JWSUserUtils.signatureAlgorithm = SignatureAlgorithm.forName(signatureAlgorithm);
        JWSUserUtils.secretKey = secretKey;
    }
    
    public static Optional<IMUser> getSysUserOptional() {
        try {
            String token = HttpContext.getRequest().getHeader(authHeader);
            Jwt<Header<?>, Claims> jwtContent = JWSUtils.parse(token, secretKey, 0L);
            return Optional.of(JSONUtils.parseObject(JSONUtils.toJSONString(jwtContent.getBody()), IMUser.class));
        } catch (Exception exception) {
            log.error("Failed to get current user info", exception);
            return Optional.empty();
        }
    }

    public static IMUser getSysUser() {
        return getSysUserOptional().orElse(null);
    }
    
    public static Long getSysUserId() {
        return getSysUserOptional().map(IMUser::getId).orElse(null);
    }

    public static IMUser checkGetSysUser() {
        return getSysUserOptional().orElseThrow(() -> new BusinessException("获取当前系统用户失败"));
    }

    public static Long checkGetSysUserId() {
        return getSysUserOptional().map(IMUser::getId).orElseThrow(() -> new BusinessException("获取当前系统用户失败"));
    }
}

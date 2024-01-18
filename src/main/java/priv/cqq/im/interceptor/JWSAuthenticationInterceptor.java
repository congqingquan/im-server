package priv.cqq.im.interceptor;

import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.cqq.oplibrary.web.exception.UnauthenticatedException;
import org.cqq.oplibrary.web.util.JWSUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import priv.cqq.im.annotation.Authentication;
import priv.cqq.im.config.JWSConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * JWS auth interceptor
 *
 * @author Qingquan.Cong
 */
@Component
@AllArgsConstructor
public class JWSAuthenticationInterceptor implements HandlerInterceptor {

    private final JWSConfig jwsConfig;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        Authentication annotation = method.getAnnotation(Authentication.class);
        if (annotation != null) {
            if (!annotation.require()) {
                return true;
            }
        }

        String authHeader = jwsConfig.getAuthHeader();
        String token = request.getHeader(authHeader);
        if (StringUtils.isBlank(token)) {
            throw new UnauthenticatedException("未认证");
        }
        JWSUtils.TokenStatus tokenStatus = JWSUtils.getTokenStatus(token, SignatureAlgorithm.forName(jwsConfig.getSignatureAlgorithm()), jwsConfig.getSecretKey());
        if (JWSUtils.TokenStatus.EXPIRED == tokenStatus) {
            throw new UnauthenticatedException("Token expired");
        } else if (JWSUtils.TokenStatus.INVALID == tokenStatus) {
            throw new UnauthenticatedException("Invalid Token");
        }
        return true;
    }
}
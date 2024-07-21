package priv.cqq.im.component.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.cqq.openlibrary.common.exception.UnauthenticatedException;
import org.cqq.openlibrary.common.util.JWSUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import priv.cqq.im.annotation.Authentication;
import priv.cqq.im.config.JWSAuthenticationConfig;

import java.lang.reflect.Method;

/**
 * JWS auth interceptor
 *
 * @author Qingquan.Cong
 */
@Component
@AllArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {
    
    private JWSAuthenticationConfig authenticationConfig;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }
        
        Method method = handlerMethod.getMethod();
        Authentication annotation = method.getAnnotation(Authentication.class);
        if (annotation != null) {
            if (!annotation.require()) {
                return true;
            }
        }
        
        // 1. 登陆鉴权
        String token = request.getHeader(authenticationConfig.getAuthHeader());
        if (StringUtils.isBlank(token)) {
            throw new UnauthenticatedException("未登陆");
        }
        JWSUtils.ParseResult parseResult = JWSUtils.parseToken(token, authenticationConfig.getSecretKey(), 0L);
        JWSUtils.TokenStatus tokenStatus = parseResult.getTokenStatus();
        if (JWSUtils.TokenStatus.EXPIRED == tokenStatus) {
            throw new UnauthenticatedException("Expired token");
        } else if (JWSUtils.TokenStatus.INVALID == tokenStatus) {
            throw new UnauthenticatedException("Invalid Token");
        }
        
        return true;
    }
}
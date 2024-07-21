package priv.cqq.im.config;

import lombok.AllArgsConstructor;
import org.cqq.openlibrary.common.filter.CommonFilter;
import org.cqq.openlibrary.common.util.CollectionUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import priv.cqq.im.component.interceptor.AuthenticationInterceptor;

import java.util.Collections;

/**
 * Web 应用程序配置
 *
 * @author Qingquan.Cong
 */
@AllArgsConstructor
@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class WebApplicationConfig implements WebMvcConfigurer {

    private final AuthenticationInterceptor authenticationInterceptor;

    public static final String[] SWAGGER_URLS = {
            "/error", "/swagger-resources/**", "/v3/api-docs/**", "/favicon.ico", "/doc.html", "/webjars/**", "/swagger-ui/**"
    };
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor)
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        CollectionUtils.addAll(
                                CollectionUtils.newArrayList(SWAGGER_URLS), "/im/c/user/login"
                        )
                );
    }
    
    @Bean
    public FilterRegistrationBean<CommonFilter> globalFilterFilter() {
        CommonFilter.CrossOriginConfig crossOriginConfig = new CommonFilter.CrossOriginConfig(
                request -> request.getHeader("Origin"),
                true,
                3600,
                CommonFilter.CrossOriginConfig.DEFAULT_ALLOW_METHODS,
                CollectionUtils.addAll(CommonFilter.CrossOriginConfig.DEFAULT_ALLOW_HEADERS, "Token")
        );

        FilterRegistrationBean<CommonFilter> filterBean = new FilterRegistrationBean<>();
        filterBean.setName("CommonFilter");
        filterBean.setFilter(new CommonFilter(crossOriginConfig));
        filterBean.setUrlPatterns(Collections.singletonList("/*"));
        return filterBean;
    }
}
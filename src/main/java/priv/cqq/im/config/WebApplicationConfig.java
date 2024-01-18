package priv.cqq.im.config;

import org.cqq.oplibrary.web.CommonFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Collections;
import java.util.List;

/**
 * Web application config
 *
 * @author Qingquan.Cong
 */
@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class WebApplicationConfig implements WebMvcConfigurer {

    private final List<HandlerInterceptor> handlerInterceptors;

    public WebApplicationConfig(List<HandlerInterceptor> handlerInterceptors) {
        this.handlerInterceptors = handlerInterceptors;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        for (HandlerInterceptor handlerInterceptor : handlerInterceptors) {
            registry.addInterceptor(handlerInterceptor)
                    .addPathPatterns("/**")
                    .excludePathPatterns(
                            "/authenticate/**",
                            "/error", "/swagger-resources/**", "/v3/api-docs", "/favicon.ico", "/doc.html", "/webjars/**",
                            "/swagger-ui/**"
                    );
        }
    }

    @Bean
    public FilterRegistrationBean<CommonFilter> globalFilterFilter() {
        FilterRegistrationBean<CommonFilter> filterBean = new FilterRegistrationBean<>();
        filterBean.setFilter(new CommonFilter());
        filterBean.setUrlPatterns(Collections.singletonList("/*"));
        return filterBean;
    }
}
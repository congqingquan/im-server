package priv.cqq.im.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalTime;

/**
 * Swagger config
 *
 * @author Qingquan.Cong
 */
@EnableOpenApi
@EnableKnife4j
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
@Configuration
public class Swagger2Config {

    /**
     * Swagger
     *          3.0.x 访问地址：http://ip:port/{context-path}/swagger-ui/index.html
     *          2.9.x 访问地址：http://ip:port/{context-path}/swagger-ui.html
     *
     * Knife4j 访问地址：http://ip:port/{context-path}/doc.html
     * Knife4j 点击空白页：@Api 注解配置中不要出现符号 '/' 即可解决
     */

    @Value("${swagger.enable}")
    private boolean enable;

    @Value("${swagger.title}")
    private String title;

    @Value("${swagger.description}")
    private String description;

    @Value("${swagger.version}")
    private String version;

    /**
     * 创建API
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30)
            .apiInfo(apiInfo())
            // 解决 LocalTime 的文档展示格式
            .directModelSubstitute(LocalTime.class, String.class)
            .select()
            // 需要暴露的API
            .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
            .paths(PathSelectors.any())
            .build()
            // 是否启用API文档
            .enable(enable);
    }

    /**
     * 创建基本信息
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            // 标题
            .title(title)
            // 描述
            .description(description)
            // 联系人
            //.contact(new Contact("", "", ""))
            // 版本
            .version(version)
            .build();
    }
}
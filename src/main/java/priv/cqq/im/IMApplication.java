package priv.cqq.im;

import org.cqq.openlibrary.common.config.SystemInitialization;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ComponentScan(value = "priv.cqq.im.**")
@MapperScan(basePackages = "priv.cqq.im.**.dao")
@EnableAspectJAutoProxy(exposeProxy = true)
@EnableTransactionManagement
@SpringBootApplication
public class IMApplication {

    public static void main(String[] args) {
        SystemInitialization.init();
        SpringApplication.run(IMApplication.class, args);
    }
}
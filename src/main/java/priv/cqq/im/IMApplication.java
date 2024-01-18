package priv.cqq.im;

import org.cqq.openlibrary.core.config.SystemInitialization;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ComponentScan(value = "priv.cqq.im.**")
@MapperScan(basePackages = "priv.cqq.im.**.dao")
@EnableTransactionManagement
@SpringBootApplication
public class IMApplication {

    public static void main(String[] args) {
        SystemInitialization.init();
        SpringApplication.run(IMApplication.class, args);
    }

}
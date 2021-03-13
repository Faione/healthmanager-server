package stu.swufe.healthmanager;

import lombok.extern.log4j.Log4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import stu.swufe.healthmanager.util.IDWorker;
import stu.swufe.healthmanager.util.RedisUtil;
import stu.swufe.healthmanager.util.TokenStorage;

@Log4j
@EnableSwagger2
@MapperScan("stu.swufe.healthmanager.dao")
@SpringBootApplication
public class HealthmanagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(HealthmanagerApplication.class, args);
    }
    @Bean
    public IDWorker createIDWorker(){
        return new IDWorker(0, 0, 1000);
    }

    @Bean
    public RedisUtil createRedisUtil(){
        return new RedisUtil();
    }

    @Bean
    public BCryptPasswordEncoder createBCryptPasswordEncoder(){return new BCryptPasswordEncoder();}

    @Bean
    public TokenStorage createTokenStorage(){return new TokenStorage();};
}

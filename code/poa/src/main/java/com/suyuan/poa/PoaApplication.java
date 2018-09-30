package com.suyuan.poa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * spring boot startup class.
 */
@EnableTransactionManagement(proxyTargetClass = true)
@SpringBootApplication
public class PoaApplication {

    /**
     * sprint boot启动入口。
     * @param args 全局参数
     */
    public static void main(String[] args) {
        SpringApplication.run(PoaApplication.class, args);
    }
}

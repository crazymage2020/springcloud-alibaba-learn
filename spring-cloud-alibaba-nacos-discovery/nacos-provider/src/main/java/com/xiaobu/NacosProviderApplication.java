package com.xiaobu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Nacos provider application.
 *
 * @author tanhongwei
 */
@SpringBootApplication
@EnableDiscoveryClient
public class NacosProviderApplication {


    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(NacosProviderApplication.class, args);
    }

    @RestController
    static class TestController {

        /**
         * Hello string.
         *
         * @param name the name
         * @return the string
         */
        @GetMapping("/hello/{name}")
        public String hello(@PathVariable String name) {
            return String.format("nacos-provider,%s", name);
        }
    }
}
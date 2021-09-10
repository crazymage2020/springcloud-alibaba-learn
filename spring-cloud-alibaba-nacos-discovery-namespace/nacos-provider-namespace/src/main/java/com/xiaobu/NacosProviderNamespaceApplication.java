package com.xiaobu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class NacosProviderNamespaceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacosProviderNamespaceApplication.class, args);
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

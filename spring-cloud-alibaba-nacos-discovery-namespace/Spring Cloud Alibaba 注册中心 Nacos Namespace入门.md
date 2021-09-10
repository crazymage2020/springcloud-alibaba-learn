##         

添加命名空间
![Screenshot_1.png](https://i.loli.net/2021/09/10/udOTPLFeSUA49hs.png)

## spring-cloud-alibaba-nacos-discovery-namespace 信息

pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://maven.apache.org/POM/4.0.0"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>com.xiaobu</groupId>
        <artifactId>springcloud-alibaba-learn</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </parent>
    <groupId>com.xiaobu</groupId>
    <artifactId>spring-cloud-alibaba-nacos-discovery-namespace</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>spring-cloud-alibaba-nacos-discovery-namespace</name>
    <description>spring-cloud-alibaba-nacos-discovery-namespace</description>
    <packaging>pom</packaging>


    <modules>
        <module>nacos-provider-namespace</module>
        <module>nacos-consumer-namespace</module>
    </modules>
</project>

```

## nacos-provider-namespace

pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://maven.apache.org/POM/4.0.0"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>com.xiaobu</groupId>
        <artifactId>spring-cloud-alibaba-nacos-discovery-namespace</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </parent>
    <groupId>com.xiaobu</groupId>
    <artifactId>nacos-provider-namespace</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>nacos-provider-namespace</name>
    <description>nacos-provider-namespace</description>
    <properties>
        <spring.boot.version>2.2.4.RELEASE</spring.boot.version>
        <spring.cloud.version>Hoxton.SR1</spring.cloud.version>
        <spring.cloud.alibaba.version>2.2.0.RELEASE</spring.cloud.alibaba.version>
    </properties>
    <!--
        引入 Spring Boot、Spring Cloud、Spring Cloud Alibaba 三者 BOM 文件，进行依赖版本的管理，防止不兼容。
        在 https://dwz.cn/mcLIfNKt 文章中，Spring Cloud Alibaba 开发团队推荐了三者的依赖关系
     -->
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-parent</artifactId>
                <version>${spring.boot.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring.cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <dependency>
                <groupId>com.alibaba.cloud</groupId>
                <artifactId>spring-cloud-alibaba-dependencies</artifactId>
                <version>${spring.cloud.alibaba.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <dependencies>
        <!-- 引入 SpringMVC 相关依赖，并实现对其的自动配置 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <!-- 引入 Spring Cloud Alibaba Nacos Discovery 相关依赖，将 Nacos 作为注册中心，并实现对其的自动配置 -->
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
        </dependency>
        <!-- 引入 Spring Cloud Alibaba config 相关依赖 -->
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
        </dependency>
    </dependencies>

</project>

```

application.properties

```properties
spring.profiles.active=dev
# 不加则注册不进去
spring.application.name=nacos-provider-namespace

```

application-dev.properties

```properties
#nacos注册中心地址
spring.cloud.nacos.discovery.server-addr=127.0.0.1:8848
#namespace
spring.cloud.nacos.discovery.namespace=3aafe1ff-9715-4c49-9b98-fcd5492d7050
#应用程序端口
server.port=18080

```

application-prod.properties

```properties
#nacos注册中心地址
spring.cloud.nacos.discovery.server-addr=127.0.0.1:8848
#namespace
spring.cloud.nacos.discovery.namespace=afe0f112-ae2e-49cd-96e7-f89abd581a39
#应用程序端口
server.port=18080


```

application-uat.properties

```properties
#nacos注册中心地址
spring.cloud.nacos.discovery.server-addr=127.0.0.1:8848
#namespace
spring.cloud.nacos.discovery.namespace=afd5a253-e3a4-4ebb-9a86-582c39bf4c82
#应用程序端口
server.port=18080


```

启动类
> @EnableDiscoveryClient 注解，开启 Spring Cloud 的注册发现功能。不过从 Spring Cloud Edgware 版本开始，实际上已经不需要添加 @EnableDiscoveryClient 注解，只需要引入 Spring Cloud 注册发现组件，就会自动开启注册发现的功能。例如说，我们这里已经引入了 spring-cloud-starter-alibaba-nacos-discovery 依赖，就不用再添加 @EnableDiscoveryClient 注解了。

```java
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

```

## nacos-consumer-namespace

pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://maven.apache.org/POM/4.0.0"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>com.xiaobu</groupId>
        <artifactId>spring-cloud-alibaba-nacos-discovery-namespace</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </parent>
    <groupId>com.xiaobu</groupId>
    <artifactId>nacos-consumer-namespace</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>nacos-consumer-namespace</name>
    <description>nacos-consumer-namespace</description>
    <properties>
        <spring.boot.version>2.2.4.RELEASE</spring.boot.version>
        <spring.cloud.version>Hoxton.SR1</spring.cloud.version>
        <spring.cloud.alibaba.version>2.2.0.RELEASE</spring.cloud.alibaba.version>
    </properties>
    <!--
        引入 Spring Boot、Spring Cloud、Spring Cloud Alibaba 三者 BOM 文件，进行依赖版本的管理，防止不兼容。
        在 https://dwz.cn/mcLIfNKt 文章中，Spring Cloud Alibaba 开发团队推荐了三者的依赖关系
     -->
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-parent</artifactId>
                <version>${spring.boot.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring.cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <dependency>
                <groupId>com.alibaba.cloud</groupId>
                <artifactId>spring-cloud-alibaba-dependencies</artifactId>
                <version>${spring.cloud.alibaba.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <dependencies>
        <!-- 引入 SpringMVC 相关依赖，并实现对其的自动配置 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <!-- 引入 Spring Cloud Alibaba Nacos Discovery 相关依赖，将 Nacos 作为注册中心，并实现对其的自动配置 -->
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
        </dependency>
    </dependencies>

</project>

```

application.properties

```properties
spring.profiles.active=dev
# 不加则注册不进去
spring.application.name=nacos-consumer-namespace

```

application-dev.properties

```properties
#nacos注册中心地址
spring.cloud.nacos.discovery.server-addr=127.0.0.1:8848
#namespace
spring.cloud.nacos.discovery.namespace=3aafe1ff-9715-4c49-9b98-fcd5492d7050
#应用程序端口
server.port=28080

```

application-prod.properties

```properties
#nacos注册中心地址
spring.cloud.nacos.discovery.server-addr=127.0.0.1:8848
#namespace
spring.cloud.nacos.discovery.namespace=afe0f112-ae2e-49cd-96e7-f89abd581a39
#应用程序端口
server.port=28080


```

application-uat.properties

```properties
#nacos注册中心地址
spring.cloud.nacos.discovery.server-addr=127.0.0.1:8848
#namespace
spring.cloud.nacos.discovery.namespace=afd5a253-e3a4-4ebb-9a86-582c39bf4c82
#应用程序端口
server.port=28080


```

启动类

```java
package com.xiaobu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class NacosConsumerNamespaceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacosConsumerNamespaceApplication.class, args);
    }

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


    /**
     * The type Test controller.
     */
    @RestController
    public static class TestController {

        /**
         * The Rest template.
         */
        @Autowired
        private RestTemplate restTemplate;

        /**
         * Hello string.
         *
         * @param name the name
         * @return the string
         */
        @GetMapping("/hello/{name}")
        public String hello(@PathVariable String name) {
            return restTemplate.getForObject("http://nacos-provider-namespace/hello/" + name, String.class);
        }
    }
}

```

然后 使用Idea的 Http Client进行方法调用 可以看到已经成功了

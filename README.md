# Spring Boot 学习笔记 

* [Spring Boot -- GitHub](https://github.com/spring-projects/spring-boot)
* [Spring 官网](https://spring.io/projects)
* [Spring Boot 中文文档](http://blog.geekidentity.com/spring/spring_boot_translation/)
* [Spring Boot 参考指南](http://oopsguy.com/documents/springboot-docs/1.5.4/index.html)
   
### 简介 
> Spring Boot可以基于Spring轻松创建可以“运行”的、独立的、生产级的应用程序。 对Spring平台和第三方类库我们有自己看法和意见（约定大于配置），所以你最开始的时候不要感到奇怪。大多数Spring Boot应用程序需要很少的Spring配置
您可以使用Spring Boot创建可以使用java -jar或传统 war 包部署启动的Java应用程序。

**我们的主要目标是:**
* 为所有的Spring开发者提供一个更快，更广泛接受的入门体验。
* 开始使用开箱即用的配置（极少配置甚至不用配置），但随着需求开始配置自己所需要的值（即所有配置都有默认值，同时也可以根据自己的需求进行配置）。
* 提供大量项目中常见的一系列非功能特征（例如嵌入式服务器，安全性，指标，运行状况检查，外部化配置）。
* 绝对没有代码生成，也不需要XML配置。
    
### 起步
```xml
<!--指定项目parent为 spring-boot-starter-parent -->
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>1.5.2.RELEASE</version>
</parent>
<!-- 添加springboot web 组件 -->
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
</dependencies>
```
```java
@Controller
@EnableAutoConfiguration  //开启springboot 自动配置
public class SampleController {

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World!";
    }
    
    //项目启动入口
    public static void main(String[] args) throws Exception {
        SpringApplication.run(SampleController.class, args);
    }
}
```

   
##JPA
> * Java Persistence API : 用于对象持久化的 API
> * JPA是一个规范(java应用程序访问ORM框架的规范)，不是框架
> * hibernate ,Spring data jpa 是JPA的实现
### Spring Data 



    

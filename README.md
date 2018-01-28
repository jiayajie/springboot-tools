# Spring Boot 学习笔记 

* [Spring Boot GitHub](https://github.com/spring-projects/spring-boot)
* [Spring 官网](https://spring.io/projects)
* [Spring Boot 中文文档](http://blog.geekidentity.com/spring/spring_boot_translation/)
* [Spring Boot 参考指南](http://oopsguy.com/documents/springboot-docs/1.5.4/index.html)
   
### 简介 
> &emsp;&emsp;Spring Boot可以基于Spring轻松创建可以“运行”的、独立的、生产级的应用程序。 对Spring平台和第三方类库我们有自己看法和意见（约定大于配置）,所以你最开始的时候不要感到奇怪。大多数Spring Boot应用程序需要很少的Spring配置
您可以使用Spring Boot创建可以使用java -jar或传统 war 包部署启动的Java应用程序。

#### 我们的主要目标是:
* 为所有的Spring开发者提供一个更快,更广泛接受的入门体验。
* 开始使用开箱即用的配置（极少配置甚至不用配置）,但随着需求开始配置自己所需要的值（即所有配置都有默认值,同时也可以根据自己的需求进行配置）。
* 提供大量项目中常见的一系列非功能特征（例如嵌入式服务器,安全性,指标,运行状况检查,外部化配置）。
* 绝对没有代码生成,也不需要XML配置。
    
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

`以上是 springboot 的一个快速入门 , 接下来聊一下spring的发展史,有助于理解springboot的由来`
## Spring的发展
#### Spring1.x 时代
> 在Spring1.x时代,都是通过xml文件配置bean,随着项目的不断扩大,需要将xml配置分放到不同的配置文件中,需要频繁的在java类和xml配置文件中切换。
#### Spring2.x时代
> 随着JDK 1.5带来的注解支持,Spring2.x可以使用注解对Bean进行申明和注入,大大的减少了xml配置文件,同时也大大简化了项目的开发。
 
> 那么,问题来了,究竟是应该使用xml还是注解呢？

 > 最佳实践：
 > * 应用的基本配置用xml,比如:数据源、资源文件等
 > * 业务开发用注解,比如:Service中注入bean等
#### Spring3.x到Spring4.x
> 从Spring3.x开始提供了Java配置方式,使用Java配置方式可以更好的理解你配置的Bean,现在我们就处于这个时代,并且Spring4.x和Spring boot都推荐使用java配置的方式。

#### Spring 的 Java Config
> Java配置是Spring4.x推荐的配置方式,可以完全替代xml配置。
> @Configuration 和 @Bean

Spring的Java配置方式是通过 @Configuration 和 @Bean 这两个注解实现的
> * @Configuration 作用于类上,相当于一个xml配置文件
> * @Bean 作用于方法上,相当于xml配置中的 \<bean\>
**事例**
```java
@Configuration //通过该注解来表明该类是一个Spring的配置，相当于一个xml文件
public class SpringConfig {
    @Bean // 通过该注解来表明是一个Bean对象，相当于xml中的<bean>
    public UserDAO getUserDAO(){
        return new UserDAO(); // 直接new对象做演示
    }
}
```
```java
@Service
public class UserService {
    @Autowired // 注入Spring容器中的bean对象
    private UserDAO userDAO;
}
```



   
## JPA
> * Java Persistence API : 用于对象持久化的 API
> * JPA是一个规范(java应用程序访问ORM框架的规范),不是框架
> * hibernate,Spring data jpa 是JPA的实现
### Spring Data 



    

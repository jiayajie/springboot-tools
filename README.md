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
> 从Spring3.x开始提供了Java配置方式,使用Java配置方式可以更好的理解你配置的Bean,并且Spring4.x和Spring boot都推荐使用java配置的方式。

#### Spring 的 Java Config
> Java配置是Spring4.x推荐的配置方式,可以完全替代xml配置。
> @Configuration 和 @Bean

Spring的Java配置方式是通过 @Configuration 和 @Bean 这两个注解实现的
> * @Configuration 作用于类上,相当于一个xml配置文件
> * @Bean 作用于方法上,相当于xml配置中的 \<bean\>

**示例**
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

#### Spring Boot 获取参数
```yaml
#普通参数
param: dongyapfeng

#list或者数组参数
listparam: p1,p2,p3

#对象形式的参数
user:
  name: dongyaofeng
  age: 18
```
> 接收方式
```java

/*第一方式种获取参数*/
@Value("${param}")
private String param;

/*第二种方式获取参数(springboot 启动后会将所有的数据封装到Enviroment 对象中)*/
@Autowired
private Environment env;

String param = env.getProperty("param");
List array = env.getProperty("listparam", List.class);
String username = env.getProperty("user.name");

/*第三种方式获取参数  需要提供User get set */
@ConfigurationProperties(prefix = "user")
```


# Spring Boot 原理解析
## 常用注解
> * @SpringBootApplication //Spring Boot项目的核心注解，主要目的是开启自动配置。
> * @EnableAutoConfiguration  //开启是品牌spring boot 自动配置功能
> * @ComponentScan  //组件扫描,并加入到程序上下文, 默认扫描该注解所在目录和同级目录的子目录
> * @Configuration  //等同于spring的XML配置文件
> * @Bean //用@Bean标注方法等价于XML中配置的bean
> * @Import //用来导入其他配置类
@ImportResource：用来加载xml配置文件

## Starter pom
>  Spring Boot为我们提供了简化企业级开发绝大多数场景的 Starter POM , 只要使用了应用场景所需要的 stater ,相关技术配置就会消除, 就可以得到spring boot 为我们提供的自动配置的bean ,因此，我们不要手动指定依赖，只需要添加一个 starter 即可，如下所示：
 ```xml
      <!--让我们来看看 REST 服务开发。
      我们可以使用像 Spring MVC、Tomcat 和 Jackson 这样的库，这对于单个应用程序来说是还是存在许多依赖
      使用 starter 效果
       -->
   <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
```
```java
@RestController
class HelloController {

    @GetMapping("/users/{id}")
    public User findUserByid(@PathVariable Integer id) {

        return null;
    }
    @PostMapping("/order")
    public void addEntity() {
        System.out.println("订单post请求");
    }
}
```
 

> Starter 不需要指定版本,Spring Boot 会自动选择合适的版本 ,仅需要指定 spring-boot-starter-parent-artifact 的版本。 如果之后您想要升级 Boot 库和依赖，只需在一个地方升级 Boot 版本即可，它将会处理其余部分



> [更多starter 详细,请参考](http://blog.csdn.net/u014430366/article/details/53648139)

## spring 自动配置类

   
# JPA
> * Java Persistence API : 用于对象持久化的 API
> * JPA是一个规范(java应用程序访问ORM框架的规范),不是框架
> * hibernate,Spring data jpa 是JPA的实现
### Spring Data 
> Spring data 的子项目学习 请点击[spring-data](https://github.com/dongyaofeng/spring-data)


# 关于微服务
## Spring Cloud介绍
> Spring Cloud是一个基于Spring Boot实现的云应用开发工具，它为基于JVM的云应用开发中的配置管理、服务发现、断路器、智能路由、微代理、控制总线、全局锁、决策竞选、分布式会话和集群状态管理等操作提供了一种简单的开发方式。

> 学习请点击[springcloud-example](https://github.com/dongyaofeng/springcloud-example)



    

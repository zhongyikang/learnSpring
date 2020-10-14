# 问题

## 为什么@Valid注解会在Bean中？ 我们在前端使用它。
前台的内容通过form表单提交到后台， 被容器接收， 把消息体解析为response中的属性（key-value对）。当Controller接受的时候， 自动组装成一个对象Order。 这时候Order有一个@Valid注解



# 介绍

tacos使用SpringBoot + Thymeleaf构建了一个非常简单的SpringBoot项目。接下来是对这个项目的介绍。

首先是TacoApplication. 类上面有一个@SpringBootApplication的注解。 这个注解是三个注解的结合体：

1. ComponentScan -- 组件扫描，默认扫描同包和子包
2. 自动装配
3. Configuration， 作为配置类， 配置bean注册到容器中。

当一个SpringBoot项目执行的时候，执行的就是带有@SpringBootApplication注解的main方法。里面有模板代码。



如果一个控制器， 只接受一个url， 然后得到特定的视图， 不需要任何中间步骤， 我们可以不使用繁琐的@RequestMapping， 自己写一个控制类， 转而使用配置类。 这种页面很少， 一般就是一个项目前台的主页。

我们自己写一个配置类，添加@WebConfig注解，同时这个类实现了WebMvcConfigurer（定义类几个配置SpringMVC的方法）。



tacos运行过程：

1. 发送一个请求到/design + GET， 出现一个design页面。
2. 填写信息， form表单提交， 发送一个/design + POST请求。
3. 重定向到/order + GET， 填写订单。
4. 发送form表单， 发送/order + POST请求。





Spring 提供了一个强大的 web 框架，称为 Spring MVC，可以用于开发 Spring 应用程序的 web 前端。

Spring MVC 是基于注解的，可以使用 @RequestMapping、@GetMapping 和 @PostMapping 等注解来声明请求处理方法。

大多数请求处理方法通过返回视图的逻辑名称来结束，例如一个 Thymeleaf 模板，请求（以及任何模型数据）被转发到该模板。

Spring MVC 通过 Java Bean Validation API 和 Hibernate Validator 等验证 API 的实现来支持验证。

视图控制器可以用来处理不需要模型数据或处理的 HTTP GET 请求。

除了 Thymeleaf，Spring 还支持多种视图选项，包括 FreeMarker、Groovy Templates 和 Mustache。
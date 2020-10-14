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





# 数据库

这里使用的是H2数据库， 需要在Pom文件中添加依赖， 然后什么都不要做， SpringBoot项目启动的时候自动创建。

创建的shema和data内容，放在src/main/resources目录下就可以了。 

### H2数据库有两大好处

1. 每次启动的时候生成数据库，非常适用于测试。
2. 没有繁琐的配置，只需要在Pom中添加一个依赖。



# 持久化

持久化一般分为两个部分：

1. 接口Repository
2. Repository的实现



Controller负责处理前台的信息， 信息会通过RepositoryImp和数据库交互。

控制层 ---- DAO----sql---数据库



Spring有两种方式和数据库交互， JDBC和JPA。

这里先谈论JDBC， 有一个JdbcTemplate， 实现了对JDBC的封装，减少了很多样板代码。 SimpleTemplate实现了对JdbcTemplate的封装，更为简单。

```java
 PreparedStatementCreatorFactory psFactory = new PreparedStatementCreatorFactory(
                "insert into Taco (name, createdAt) values (?, ?)",
                Types.VARCHAR, Types.TIMESTAMP);
        
        psFactory.setReturnGeneratedKeys(true);
        
        PreparedStatementCreator psc = psFactory.newPreparedStatementCreator(
            Arrays.asList(
                taco.getName(),
                new Timestamp(taco.getCreatedAt().getTime())));

 	//keyHolder， 获取自动生成的keys
        KeyHolder keyHolder = new GeneratedKeyHolder();
        
        //通过psc和keyholder来update
        jdbc.update(psc, keyHolder);
```

以上JdbcTemplate代码完成了insert操作。首先，创建一个`PreparedStatementCreatorFactory`对象，里面存放了sql语句样板。 然后通过`newPreparedStatementCreator`方法，传入一个list， 得到一个PSC。最后调用update，传入psc和KeyHolder。

1. PSCF， 样板sql语句。
2. 通过方法， 传入List形式的参数， 得到PSC
3. PSC和KeyHolder， 传入jdbcTemplate的update方法， 完成update操作。 同时， 自动生成的key传入keyHolder。

### SimpleJDBCTemplate

```java
this.orderInserter = new SimpleJdbcInsert(jdbc) //初始化一个insert功能的SimpleJdbc， 需要JdbcTemplate实例作为参数
            .withTableName("Taco_Order")//操作的表名
            .usingGeneratedKeyColumns("id");//主键
```

我们思考一般的update操作的格式：

```java
 "insert into Ingredient (id, name, type) values (?, ?, ?)"
```

通过以上操作， 我们已知： insert into Taco_Order, 现在只差添加的的参数了。



首先我们知道， 一个Bean的properties和数据库对应表的column的key是保持一致的。

通过jackson提供的ObjectMapper， 我们可以把一个对象（这里是Order），转化为Map形式。

```Java
import com.fasterxml.jackson.databind.ObjectMapper;

 private long saveOrderDetails(Order order) {
        @SuppressWarnings("unchecked") 
        Map<String, Object> values = objectMapper.convertValue(order, Map.class);
        //OjbectMaper是Jackson对象，它的作用是把order转化为Map
         
        values.put("placedAt", order.getPlacedAt()); 
     //ObjectMapper会将Data转化为Long类型，所以这里重新put是必要的
        
        long orderId = orderInserter.executeAndReturnKey(values)/*通过传入的MapOfOrder， 执行insert操作*/.longValue();//返回longValue。
        
        return orderId;
    }

 //"SimpleJdbcInsert into table_name (Map.key) values (Map.value)"
```

总结： 获取SimpleJdbcInsert， 然后设置操作表， 把一个对象通过ObjectMapper转化为Map， 作为参数执行。

如果想要获得主键， 可以在SimpleJdbcInsert中设置，然后使用executeAndReturnKey方法。

# Converter

当填写完design.html对应在浏览器中的form的时候， 每种checkedBox选项， 都有同一个name， “ingredients”， 也就是说传到后台的是一个叫做“ingredients”的string[]。 它要与Taco匹配，作为参数传入对应的Controller中。

这中间有一个自动的convert的过程， 如果不做任何设定， 请求体或者jackson中的name对应的value会被赋值到 创建的Taco实例中的名字相同的field。



如果找不到， Taco实例有部分field为null。假设设定了@Valid， 则可能抛出异常。

在此程序中， 前台传入的ingredients是String[]类型的，但是Taco类里面的对应对象确是List<Ingredient>，无法做到自动转换。 所以在tacos/web手动实现了一个convert，专门用于把String对象转化为Ingredient对象。

```java

@Component
public class IngredientByIdConverter implements Converter<String, Ingredient> {

  private IngredientRepository ingredientRepo;

  @Autowired
  public IngredientByIdConverter(IngredientRepository ingredientRepo) {
    this.ingredientRepo = ingredientRepo;
  }
  
  @Override
  public Ingredient convert(String id) {
    return ingredientRepo.findById(id);
  }

}

```








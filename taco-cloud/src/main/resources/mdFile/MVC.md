## DesignTacoController

### 注解

1. Slf4j: lombok 提供的日志注解，创建一个Log对象，可以用来在console打印日志信息。
2. Controller： 被组件扫描
3. RequestMapping一般放在类上面指定基础路径， 其他具体的例如PostMapping、GetMapping放在类路径中。



# Thymeleaf内容

```html
<img th:src="@{/images/a.jpg}"/>   
<!--img的链接， 使用@{}形式， 里面存放路径-->

<form method="POST" th:object="${design}">
 <!--${}操作符请求属性的值，这里的属性是response的属性，来自于Model， 也就是控制层处理后传给Model的内容-->
    
   <div th:each="ingredient : ${wrap}"> 
   <!--wrap是一个存放ingredient属性对象的list， th:each的作用是遍历， -->
   
   <input name="ingredients" type="checkbox" 		    			th:value="${ingredient.id}"/>
   
   <!--这里还在th：each的迭代中。th：value是input文本框中的内容。 th：text是span的文本内容。-->
   <span th:text="${ingredient.name}">INGREDIENT</span><br/>
    </div>
```

## 零散
1. img 的src属性后面的路径， 指的是src/main/resouces/static作为根目录的路径。




# Java验证机制 javax.validation.constraints

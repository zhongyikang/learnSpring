# JPA



## 实体类

1. @Prepersist的作用是什么？

2. 为什么Order类有一个`@Table`注解？

   如果没有这个@Table， 这个实体类会默认保存到同名数据表中。 例如： Order会保存到表`Order`。`@Table`注解的作用是设置其自动保存的数据表为`Taco_Order`。

3. 为什么Order的TacoList的注解是 `@ManyToMany`?
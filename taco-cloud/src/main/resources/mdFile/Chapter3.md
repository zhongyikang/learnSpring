# Chapter3

第三章主要内容是Spring对于持久化的支持， 分为JPA和JDBC。

Spring通过JdbcTemplate支持Jdbc。

### 使用JdbcTemplate查询数据库， 不需要繁杂的样板代码

```java
private JdbcTemplate jdbc; //一个field， 是JdbcTemplate
​
@Override
public Ingredient findOne(String id) {
    return jdbc.queryForObject(
        "select id, name, type from Ingredient where id=?",
        this::mapRowToIngredient, id);
}
​
private Ingredient mapRowToIngredient(ResultSet rs, int rowNum)
    throws SQLException {
    return new Ingredient(
        rs.getString("id"),
        rs.getString("name"),
        Ingredient.Type.valueOf(rs.getString("type")));
}
```


## 使用技术和工具
1. jdbcTemplate
   1. 添加依赖。
   2. 创建接口（要实现的功能）
   3. 接口的实现类， 使用JdbcTemplate， 然后类有@Reponsitory注解
2. H2 Console
3. 




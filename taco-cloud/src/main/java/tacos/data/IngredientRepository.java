package tacos.data;
import org.springframework.data.repository.CrudRepository;

import tacos.Ingredient;

//自动生成insert语句和对应的方法。泛型分别是： 实体类型 + 主键类型
public interface IngredientRepository extends CrudRepository<Ingredient, String>{
	/**
	 * 四种操作，增删改查。
	 * 增加： 插入一个Ingrendient
	 * 查找：返回一个Ingredient
	 * 改、删： 插入一个Id
	 * 
	 * 每一个DAO方法有固定的结构， 例如：
	 * findById(String id). 
	 * 	find表明是select
	 * By类似于Sql语句中的where
	 * id = ？（参数列表中的id）
	 * select * from Ingredient where id = ?。 
	 * 可以看到一个sql语句的所有元素都具备了。
	 */
}


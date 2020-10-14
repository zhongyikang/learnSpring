package tacos.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import tacos.Ingredient;

@Repository
public class JdbcIngredientReponsitory implements IngredientRepository{

	private JdbcTemplate jdbc;
	
	
	@Autowired
	public JdbcIngredientReponsitory(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	@Override
	public Iterable<Ingredient> findAll() {
		return jdbc.query("select id, name, type from Ingredient",
	            this::mapRowToIngredient);
		/**
		 * 这个this::mapRowToIngredient实现的是函数式接口
		 * public <T> T query(String sql, RowMapper<T> rowMapper)里面的rowMapper。 
		 * 
		 */
		
	}

	
	private Ingredient mapRowToIngredient(ResultSet rs, int rowNum)
		    throws SQLException {
		/*一个resultSet就是一行数据， id、name都是这一行数据中的各自数据的key*/
		    return new Ingredient(
		        rs.getString("id"),
		        rs.getString("name"),
		        Ingredient.Type.valueOf/*valueOf, 通过一个String，获取对应的enum对象*/(rs.getString("type")));
	}
	
	
	@Override
	public Ingredient findById(String id) {
	    return jdbc.queryForObject(
	        "select id, name, type from Ingredient where id=?",
	        /*RowMapper的作用是根据查询到的ResultSet（一行数据）， 构建返回的对象（Ingredient）*/
	        this::mapRowToIngredient, id);
	}

	@Override
	public Ingredient save(Ingredient ingredient) {
	    jdbc.update( //参数列表（sql， args）
	        "insert into Ingredient (id, name, type) values (?, ?, ?)",
	        ingredient.getId(),
	        ingredient.getName(),
	        ingredient.getType().toString());
	    return ingredient;
	}

	
	/**
	 * 总结：
	 * 1. select对应jdbc.query.., 需要实现RowMapper。 通过sql语句可以查到数据库中的一行或者多行数据， 
	 * RowMappper的作用是用这些数据创建实例返回。
	 * 2. update、delete不需要返回resultSet，所以只要传入参数就可以了。
	 * 
	 * find---query---select
	 * save---update----insert
	 */
	

}

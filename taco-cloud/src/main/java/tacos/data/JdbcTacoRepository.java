package tacos.data;



import java.sql.Timestamp;

import java.sql.Types;
import java.util.Arrays;
import java.util.Date;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import tacos.Ingredient;
import tacos.Taco;

@Repository
public class JdbcTacoRepository implements TacoRepository {
    
    private JdbcTemplate jdbc;
    
    public JdbcTacoRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }
    /**
     * 1. 把taco存入taco表格中。
     * 2. 通过taco表格， 得到自动生成的tacoid。
     * 3. 创建tacoId和ingredientId的联系和表格taco-ingredient
     */
    @Override
    public Taco save(Taco taco) {
    	/**
    	 * 把信息存入Taco表中
    	 */
        long tacoId = saveTacoInfo(taco); //insert Taco进入表的时候， 获取一个自动生成的id， 就是tacoId。
        taco.setId(tacoId);
        for (Ingredient ingredient : taco.getIngredients()) { //对taco的每个ingredient， 创建TacoId和ingredientId的练习
            saveIngredientToTaco(ingredient, tacoId);
        }
        return taco;
    }
    
    private long saveTacoInfo(Taco taco) {
        taco.setCreatedAt(new Date());
        
        /*
         * 1. 创建一个Factory， 构造器的两个参数分别是： sql语句， sql语句的type
         * 2. 通过newPSC， 传入List形式的参数。创建一个PSCImpl。
         * PSCImpl有两个组成部分： 1. actuallStatement、2. param
         */
        PreparedStatementCreatorFactory psFactory = new PreparedStatementCreatorFactory(
                "insert into Taco (name, createdAt) values (?, ?)",
                Types.VARCHAR, Types.TIMESTAMP
            );
        
        psFactory.setReturnGeneratedKeys(true);
        
        PreparedStatementCreator psc = psFactory.newPreparedStatementCreator(
            Arrays.asList(
                taco.getName(),
                new Timestamp(taco.getCreatedAt().getTime())));
        
        
        
        //keyHolder， 获取自动生成的keys
        KeyHolder keyHolder = new GeneratedKeyHolder();
        
        //通过psc和keyholder来update
        jdbc.update(psc, keyHolder);
        
        //获取tacoId
        return keyHolder.getKey().longValue();
    }
    
    private void saveIngredientToTaco(Ingredient ingredient, long tacoId) {
        jdbc.update(
            "insert into Taco_Ingredients (taco, ingredient) " +"values (?, ?)",
            tacoId, ingredient.getId());
    }
}
package tacos.data;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;

import tacos.Order;
import tacos.Taco;

@Repository
public class JdbcOrderRepository implements OrderReponsitory{
	
	private SimpleJdbcInsert orderInserter;
    private SimpleJdbcInsert orderTacoInserter;
    private ObjectMapper objectMapper;
	
    
    @Autowired
    public JdbcOrderRepository(JdbcTemplate jdbc) {
    	
    	//创造第一个insert， 设置table和genericKey id。
        this.orderInserter = new SimpleJdbcInsert(jdbc)
            .withTableName("Taco_Order")
            .usingGeneratedKeyColumns("id");
        
        this.orderTacoInserter = new SimpleJdbcInsert(jdbc)
            .withTableName("Taco_Order_Tacos");
        
        this.objectMapper = new ObjectMapper();
    }
    
    
    @Override
    public Order save(Order order) {
        order.setPlacedAt(new Date());
        long orderId = saveOrderDetails(order); //把order插入表Taco_Order并得到orderId
        order.setId(orderId);
        System.out.println("\n\nOK1\n\n");
        List<Taco> tacos = order.getTacos();
        for (Taco taco : tacos) {
            saveTacoToOrder(taco, orderId);
        }
        
        System.out.println("\n\nOk2\n\n");
        return order;
    }
    
    private long saveOrderDetails(Order order) {
        @SuppressWarnings("unchecked") //?
        Map<String, Object> values = objectMapper.convertValue(order, Map.class);
        /**
         * OjbectMaper是Jackson对象，它的作用是把order转化为Map
         */
        values.put("placedAt", order.getPlacedAt()); //ObjectMapper会将Data转化为Long类型，所以这里重新put是必要的
        
        long orderId = orderInserter.executeAndReturnKey(values)/*通过传入的MapOfOrder， 执行insert操作*/.longValue();//返回longValue。
        
        return orderId;
    }

    private void saveTacoToOrder(Taco taco, long orderId) {
        Map<String, Object> values = new HashMap<>();
        values.put("tacoOrder", orderId);
        values.put("taco", taco.getId());
        
        orderTacoInserter.execute(values);
    }
    
    /**
     * 1. SimpleJdbcInsert分装了JdbcTemplate， 以下是使用方式。
     *   a. 通过传入JdbcTemplate， 创造SJI， 可以设置对应的表和主键。
     *   b. 把insert对象， 通过objectMapper转化为Map， 或者自行转化也可以。
     *   c. 执行execute(Map)操作。
     *   d. 如果要返回主键， 执行executeAndReturnKey(Map)操作。
     */
}

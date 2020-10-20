package tacos.data;

import org.springframework.data.repository.CrudRepository;

import tacos.Order;

public interface OrderReponsitory extends CrudRepository<Order, Long>{
}

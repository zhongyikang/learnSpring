package tacos.web;

import javax.validation.Valid;
import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import lombok.extern.slf4j.Slf4j;
import tacos.Order;
import tacos.data.OrderReponsitory;

@Slf4j //创建一个log对象。
@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderController {
	
	private OrderReponsitory orderRepo;


	@Autowired
    public OrderController(OrderReponsitory orderRepo) {
		this.orderRepo = orderRepo;
	}



	@GetMapping("/current")
    public String orderForm(Model model) {
		System.out.println("Everything's fine!");
        return "orderForm";
    }
    
    
    
    @PostMapping
    public String processOrder(@Valid Order order,Errors errors,SessionStatus sessionStatus) {
    	if(errors.hasErrors()) {
    		return "orderForm";
    	}
    	System.out.println(order);
    	orderRepo.save(order);
    	sessionStatus.setComplete(); //订单完成，把order从session中去除。
        log.info("Order submitted: " + order);
        
        return "redirect:/";
    }
}

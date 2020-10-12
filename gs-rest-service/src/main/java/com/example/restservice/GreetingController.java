package com.example.restservice;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 0. 使用restController， 而不是@Controller。 前者是RESTful控制器，后者是普通的MVC控制器。
 * MVC控制器返回的是一个view， 一般是String类型， 作为逻辑名，被视图解析器解析。
 * RESTful返回的是一个实体， 被Spring’s HTTP message converter convert为Jason对象，传到前台。
 * （org.springframework.http.converter.json.MappingJackson2HttpMessageConverter）
 * 
 * 如果是普通的MVC, 需要使用@Controller和@ResponseBody。
 * @author 钟益康
 *
 */
@RestController
public class GreetingController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@GetMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		/**
		 * 1. 从前台传入的信息被放入request， 以name-value的形式。 
		 * 这里的RequestParam的key是name。 如果找不到这个属性， 把默认的defaultValue传入name参数中。
		 */
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
		/**
		 * 2. 这里返回的是一个实体， Greeting对象， 它会被MappingJackson2HttpMessageConverter（Spring的）转化为Jason对象，Json
		 *对象会被写入到HTTP response中。
		 */
	}
}

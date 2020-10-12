package tacos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * SpringBoot主类
 * @author 钟益康
 *
 */
@SpringBootApplication
/**
 * 这个注释是三个注释的集合：
 * 1. @SpringBootConfiguration 作为配置类，可以理解为@Configuration的特殊形式
 * 2. @EnableAutoConfiguration 启用自动配置
 * 3. @ComponentScan 启用组件扫描。
 * （扫描@Component、@Service、@Controller的组件，将注册为上下文中的bean）
 * 
 * @author 钟益康
 *
 */
public class TacoCloudApplication {

	public static void main(String[] args) {
		SpringApplication.run(TacoCloudApplication.class, args);
	}

}

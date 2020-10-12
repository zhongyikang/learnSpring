package tacos;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller //表示是一个Controller,可被组件扫描注册到容器中
public class HomeController {
	@GetMapping("/") //
	public String home() {
		return "home"; //ViewName
	}
}

/**
 * 逻辑结构：
 * 1.request发送到服务器。
 * 2. 容器创建HttpRequest、HttpResponse， 把信息放在HttpRequest中，传给DispatcherServlet。
 * 2.DS找到Handler， handler通过mapping找到对应的controller。
 * 3. controller处理内容， 返回一个逻辑视图名。
 * 4. 放在ModelAndView， 逻辑视图名被解析成一个路径指向一个特定的资源（JSP或者是Thymeleaf）。
 * 5. Model的内容放在response中，在传送到前端的视图中回显。
 */

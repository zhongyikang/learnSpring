package tacos.web;

import java.util.ArrayList;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import tacos.Taco;
import tacos.data.IngredientRepository;
import tacos.data.TacoRepository;
import tacos.Ingredient;
import tacos.Ingredient.Type;
import tacos.Order;


@Controller
@RequestMapping("/design")
@SessionAttributes("order")//会把请求？响应？中的order属性放在session中。
//若希望在多个请求之间共用数据，则可以在控制器类上标注一个
//@SessionAttributes,配置需要在session中存放的数据范围，Spring MVC将存放在model中对应的数据暂存到HttpSession 中。
public class DesignTacoController {
	
	private final IngredientRepository ingredientRepo; //Q:为什么是final？
	private TacoRepository designRepo;
	
	
	
	
	
	@Autowired
    public DesignTacoController(IngredientRepository ingredientRepo, TacoRepository designRepo) {
		super();
		this.ingredientRepo = ingredientRepo;
		this.designRepo = designRepo;
	}


	@GetMapping //发送到/design 的get请求
    public String showDesignForm(Model model) {
    	ArrayList<Ingredient> ingredients = new ArrayList<>();
    	ingredientRepo.findAll().forEach(i -> ingredients.add(i));
  	
    	Type[] types = Ingredient.Type.values();
    	
    	for(Type type:types) { //存储了type的lowcase， value为具有特定ingredient的list
    		model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type)/*返回一个type为之前量的ingredient的list*/);
    	}
    	return "design";
    }
    
    
	@ModelAttribute(name = "order")
    public Order order() {
        return new Order();
    }
    
    @ModelAttribute(name = "taco") //在model中创建taco对象， name为taco
    public Taco taco() {
        return new Taco();
    }


	@PostMapping
    public String processDesign(@Valid Taco design, Errors errors,
    		@ModelAttribute/*判定有没有Order， 如果没有， 则创建一个。Order来自于Model而不是前台*/ Order order) {
    	//@Valid 注释告诉 Spring MVC 在提交的 Taco 
    	//对象绑定到提交的表单数据之后，以及调用 
    	//processDesign() 方法之前，对提交的 Taco 对象执行验证。
    	
		
		
    	if(errors.hasErrors()) {
    		System.out.println("不会吧！");
    		return "/design"; //重新填写表单
    	}
        
    	Taco saved = designRepo.save(design);
    	order.addDesign(saved); //Model中的属性。
    	
    	/**
    	 * 思考这里的Model order？ 为什么不是直接创造一个， 把taco放在这个Order中？
    	 * 回答： 如果我只能订一次Taco， 那么， 我填写taco信息， 把taco保存， 然后创建一个order存入就行了。
    	 * 但是， 这里的单个order可能订多次taco。这一次处理的时候， 可能已经有一个order， 其中的tacos field有多个taco， 
    	 * 
    	 */
    	
        return "redirect:/orders/current";
    }

    // provided by 'aexiaosong'
    private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients
        		.stream()
        		.filter(x -> x.getType().equals(type))
        		.collect(Collectors.toList());
    }
}
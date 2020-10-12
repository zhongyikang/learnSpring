package tacos;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data  //自动设置get和setter方法
@RequiredArgsConstructor //添加全参构造器
public class Ingredient {
    private final String id;
    private final String name;
    private final Type type;

    public static enum Type {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }
    
    
    /**
     * Q：
     * 1. lombok有什么用？
     * 2. 为什么这个bean中没有构造器。
     * 3.@Data和@RequeredArgsConstructor是什么意思？
     */
}
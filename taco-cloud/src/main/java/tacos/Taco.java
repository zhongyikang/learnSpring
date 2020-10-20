package tacos;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
public class Taco {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) //这个主键是自动生成的
	private Long id;
    
    private Date createdAt;
	
 	@NotNull
    @Size(min=5, message="Name must be at least 5 characters long")
    private String name;
    
 	
    @Size(min=1, message="You must choose at least 1 ingredient")
    @ManyToMany(targetEntity = Ingredient.class) //生成表Taco_Ingredients
    private List<Ingredient> ingredients = new ArrayList<>();
    
    @PrePersist //自动生成
    void createdAt() {
    	this.createdAt = new Date();
    }
}
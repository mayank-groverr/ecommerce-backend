package practice.mayank.ecommerce.entity.listeners;

import jakarta.persistence.PreRemove;
import practice.mayank.ecommerce.entity.Category;


public class CategoryListener {

    @PreRemove
    public void beforeDeletion(Category category){
        category.getProducts().forEach(product -> product.setCategory(null));
    }

}

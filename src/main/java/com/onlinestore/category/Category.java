package com.onlinestore.category;

import com.onlinestore.product.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor

public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String parentCategory;
    private String childCategory;

    @OneToMany (mappedBy = "category")
    private List<Product> products;

}

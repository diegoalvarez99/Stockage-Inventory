/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Product;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 *
 * @author dgalvarez
 */
@Table("tb_products")
public class Product {
    @Id
    @Column("pro_code")
    public Integer code;
    
    @Column("pro_name")
    public String name;
    
    @Column("pro_price")
    public Double price;
    
    @Column("pro_inventory")
    public Integer inventory;

    private Product(Integer code, String name, Double price, Integer inventory) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.inventory = inventory;
    }

    public static Product createProduct(String name, Double price, Integer inventory) {
        return new Product(null, name, price, inventory);
    }
    
    //Setters
    public void setCode(Integer code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }
    //Getters

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getInventory() {
        return inventory;
    }
    
}

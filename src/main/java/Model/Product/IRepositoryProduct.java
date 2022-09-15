/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Model.Product;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author dgalvarez
 */
//@Repository
public interface IRepositoryProduct extends CrudRepository<Product,Integer> {
 
    @Query("select sum(pro_price*pro_inventory) from db_store_g05.tb_products;")
    Double getAveragePrice();
    
    @Query("select sum(pro_price) from db_store_g05.tb_products;")
    Double getSumPrice();
    
    @Query("SELECT pro_name FROM db_store_g05.tb_products ORDER BY pro_price DESC LIMIT 1;")
    String getMaxPrice();
    
    @Query("SELECT pro_name FROM db_store_g05.tb_products ORDER BY pro_price ASC LIMIT 1;")
    String getMinPrice();
    
}

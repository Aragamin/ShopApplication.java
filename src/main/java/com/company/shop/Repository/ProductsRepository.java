package com.company.shop.Repository;

import com.company.shop.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductsRepository extends JpaRepository<Product,Long> {

    //список товаров
    @Query(value = "SELECT * FROM Products ",
            nativeQuery = true )
    List<Product> allProducts();

    //удаление продукта по id
    Long deleteByIdProduct(Integer id);


    //Ищем товар по названию
    List<Product> findAllByTitle(String title);

    //Ищем товар по id
    List<Product> findAllByIdProduct(Integer id);

    //Обновление количества товаров
    @Modifying
    @Query("UPDATE Products SET amount =:newAmount WHERE idProduct=(:idProduct)")
    Integer updateAmount(Integer idProduct, Integer newAmount);

   //статистика
    @Query(value = "select * from products inner join orderand_product on products.idProduct=orderand_product.idProduct ",
            nativeQuery = true )
    public List<Product> statProducts();
}

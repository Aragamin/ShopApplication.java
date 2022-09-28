package com.company.shop.Repository;

import com.company.shop.domain.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductsRepository extends JpaRepository<Products,Long> {

    //список товаров
    @Query(value = "SELECT * FROM Products ",
            nativeQuery = true )
    List<Products> allProducts();

    //удаление продукта по id
    Long deleteByIdProduct(Integer id);


    //Ищем товар по названию
    List<Products> findAllByTitle(String title);

    //Ищем товар по id
    List<Products> findAllByIdProduct(Integer id);

    //Обновление количества товаров
    @Modifying
    @Query("UPDATE Products SET amount = amount - 1 WHERE idProduct=(:idProduct)")
    void decrementAmountProductsWithId(Integer idProduct);

   //статистика
    @Query(value = "select * from products inner join order_and_product on products.idProduct=order_and_product.idProduct ",
            nativeQuery = true )
    List<Products> statProducts();
}

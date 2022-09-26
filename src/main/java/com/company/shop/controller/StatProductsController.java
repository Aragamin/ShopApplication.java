package com.company.shop.controller;

import com.company.shop.Service.ProductsService;
import com.company.shop.additions.StatisticsPrice;
import com.company.shop.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class StatProductsController {

    @Autowired
    ProductsService productsService;

    @GetMapping("/statProducts.html")
    public String stat( Model model) {

        List<Product> statistics = productsService.statProducts();
        //удаляем повторяющиеся продукты
        for (int i = 0; i <  statistics.size() - 1; i++) {
            // Начинаем переход вперед из списка с индексом list.size () - 1
            for (int j =  statistics.size() - 1; j > i; j--) {
                // Сравнить
                if ( statistics.get(j).equals( statistics.get(i))) {
                    // дедупликация
                    statistics.remove(j);
                }
            }
        }

        //считаем число повторений
        ArrayList<StatisticsPrice> result = new ArrayList<>();
        List<Product> temp = productsService.statProducts();
        int count=0;
        for (Product statistic : statistics) {
            for (Product product : temp) {
                if (statistic.getIdProduct().equals(product.getIdProduct())) {
                    ++count;
                }
            }
            result.add(new StatisticsPrice(statistic.getIdProduct(), count));
            count = 0;
        }
        Collections.sort(result);
        Collections.reverse(result);
        model.addAttribute("statistics",result);
        return "statProducts.html";
    }

}

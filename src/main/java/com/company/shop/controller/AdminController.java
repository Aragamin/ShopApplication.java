package com.company.shop.controller;

import com.company.shop.Service.AdminService;
import com.company.shop.Service.ProductsService;
import com.company.shop.additions.StatisticsPrice;
import com.company.shop.domain.Admin;
import com.company.shop.domain.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@SessionAttributes(types = Admin.class)
public class AdminController {
    @Autowired
    private AdminService adminService;

    @Autowired
    private ProductsService productsService;

    @GetMapping("/entryAdm.html")
    public String entryAdm( Model model) {
        return "entryAdm.html";
    }

    @PostMapping("/entryAdm.html")
    public String entryPostAdm(@RequestParam String log, @RequestParam  String psw, Model model) {
        Admin admin = adminService.findByPassAndLogin(log, psw);
        if(admin == null) {
            model.addAttribute("errorData","НЕВЕРНЫЕ ДАННЫЕ!");
            return "entryAdm.html";
        }
        model.addAttribute(admin);
        return "redirect:/menuAdm.html";
    }

    @GetMapping("/menuAdm.html")
    public String newProduct(@SessionAttribute Admin admin, Model model) {
        return "menuAdm.html";
    }

    @PostMapping("/menuAdm.html")
    public String addProductNew(@SessionAttribute Admin admin, @RequestParam String title,@RequestParam  Integer amount,@RequestParam Integer price,@RequestParam String description, Model model) {

        /////////////////////////если не пишем, то ошибка страницы
        Products newProduct = new Products(title,description, amount, price);

        if (!productsService.createProduct(newProduct) )
        {
            model.addAttribute("error","Такой товар уже существует!");
            return "menuAdm.html";
        }
        return "redirect:/menuAdm.html";
    }
    @PostMapping("/menuAdm/updateAmount.html")
    public String updateAmount(@SessionAttribute Admin admin, @RequestParam Integer amount,@RequestParam Integer id, Model model) {
        if (amount<0) {
            return "redirect:/menuAdm.html";
        }
        productsService.decrementAmountWithId(id);
        return "redirect:/menuAdm.html";
    }

    @GetMapping("/statProfit.html")
    public String statProfit(@SessionAttribute Admin admin, Model model) {
        List<Products> statistics = productsService.statProducts();
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
        List<Products> temp = productsService.statProducts();
        int count=0;
        for (Products statistic : statistics) {
            for (Products product : temp) {
                if (statistic.getIdProduct().equals(product.getIdProduct())) {
                    ++count;
                }
            }
            result.add(new StatisticsPrice(statistic.getIdProduct(), count * statistic.getPrice(), statistic.getTitle()));
            count = 0;
        }
        Collections.sort(result);
        Collections.reverse(result);
        model.addAttribute("statistics",result);
        return "statProfit.html";
    }

    @GetMapping("/statProducts.html")
    public String stat(@SessionAttribute Admin admin, Model model) {

        List<Products> statistics = productsService.statProducts();
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
        List<Products> temp = productsService.statProducts();
        int count=0;
        for (Products statistic : statistics) {
            for (Products product : temp) {
                if (statistic.getIdProduct().equals(product.getIdProduct())) {
                    ++count;
                }
            }
            result.add(new StatisticsPrice(statistic.getIdProduct(), count, statistic.getTitle()));
            count = 0;
        }
        Collections.sort(result);
        Collections.reverse(result);
        model.addAttribute("statistics",result);
        return "statProducts.html";
    }

}

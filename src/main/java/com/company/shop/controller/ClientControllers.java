package com.company.shop.controller;
import com.company.shop.Service.ClientService;
import com.company.shop.Service.OrderService;
import com.company.shop.Service.ProductsService;
import com.company.shop.domain.Client;
import com.company.shop.domain.Orders;
import com.company.shop.domain.Products;
import com.company.shop.additions.AddProd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@SessionAttributes(types = Client.class)
public class ClientControllers {

    private static final String ALL_PRODUCTS_TAG = "allProducts";

    @Autowired
    private ClientService clientService;

    @GetMapping("/entry.html")
    public String entry( Model model) {
        return "entry.html";
    }


    @PostMapping("/entry.html")
    public String entryPost(@RequestParam String log,@RequestParam  String psw,  Model model) {
        Client client = clientService.findByPassAndLogin(log, psw);
        if (client == null) {
            model.addAttribute("errorData","НЕВЕРНЫЕ ДАННЫЕ!");
            return "entry.html";
        }
        model.addAttribute(client);
        return "redirect:/menu.html";
    }

    @Autowired
    private ProductsService productsService;

    @GetMapping("/menu.html")
    public String menu(Model model) {
        List<Products> allProducts = productsService.allProducts();
        model.addAttribute(ALL_PRODUCTS_TAG, allProducts);
        return "menu.html";
    }

    @PostMapping("/menu.html")
    public String addProducts(@SessionAttribute Client client, @ModelAttribute(value="addProd") AddProd listCheck, Model model){
        List<Integer> checkedItems = listCheck.getCheckedItems();
        ArrayList<Products> newOrder = new ArrayList<>();
        List<Products> products = productsService.allProducts();
        model.addAttribute(ALL_PRODUCTS_TAG, products);
        if (checkedItems == null || checkedItems.isEmpty()) {
            model.addAttribute("errorDataMenu","НЕ ВЫБРАНЫ ПРЕДМЕТЫ ДЛЯ ПОКУПКИ!");
            return "menu.html";// выводим сообщение, что выделено 0 предметов
        }
        // валидация выбранных заказов
        for (Products p : products) {
            if (!checkedItems.contains(p.getIdProduct())) {
                continue;
            }
            if (p.getAmount() < 1) {
                // когда выбранного товара не осталось
                model.addAttribute("errorDataMenu", "Товар " + p.getTitle() + "закончился!");
                return "menu.html";
            }
        }
        Integer price = 0;
        for (Products p : products) {
            if (!checkedItems.contains(p.getIdProduct())) {
                continue;
            }
            newOrder.add(p);
            price += p.getPrice();
            productsService.updateAmount(p.getIdProduct(), p.getAmount() - 1); // todo брать количество не локально, а сразу декрементить в БД
            p.setAmount(p.getAmount() - 1);
        }

        Orders order = new Orders(client,"Принят", new Date(), price,newOrder);
        orderService.createOrder(order);
        model.addAttribute("successData","Заказ принят!");
        model.addAttribute(ALL_PRODUCTS_TAG, products);
        return "menu.html"; // выводим сообщение об успехе
    }

    @Autowired
    private OrderService orderService;

    @GetMapping("/history.html")
    public String history(@SessionAttribute Client client, Model model) {
        try{
            if (client != null) {
                List<Client> clients = clientService.findByLogin(client.getLogin());
                if (!clients.isEmpty()) {
                    List<Orders> listOrders = clients.get(0).getOrdersClient();
                    model.addAttribute("listOrders", listOrders);
                    return "history.html";
                }
            }
            else {
                // todo сообщение об ошибке 403
                model.addAttribute("errorData","НЕВЕРНЫЕ ДАННЫЕ - 403 Exception!");
                return "entry.html";
            }
        } catch (Exception e){
            System.out.println(e);
            model.addAttribute("errorData","Error 403");
            return "entry.html";
        }
        return "history.html";
    }
}

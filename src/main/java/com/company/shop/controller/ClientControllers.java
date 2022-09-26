package com.company.shop.controller;
import com.company.shop.Service.ClientService;
import com.company.shop.Service.OrderService;
import com.company.shop.Service.ProductsService;
import com.company.shop.domain.Client;
import com.company.shop.domain.Orders;
import com.company.shop.domain.Product;
import com.company.shop.additions.addProd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class ClientControllers {

    @Autowired
    private ClientService clientService;
    private Client client;

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
        return "redirect:/menu.html";
    }

    @Autowired
    private ProductsService productsService;

    @GetMapping("/menu.html")
    public String menu(Model model) {
        List<Product> allProducts = productsService.allProducts();
        model.addAttribute("allProducts",allProducts);
        return "menu.html";
    }

    @PostMapping("/menu.html")
    public String addProducts(@ModelAttribute(value="addProd") addProd listCheck, Model model){


        List<Integer> checkedItems = listCheck.getCheckedItems();
        ArrayList<Product> newOrder = new ArrayList<>();

        if(checkedItems==null){
            return "redirect:/menu.html";
        }

        for (Integer id : checkedItems) {
            Product p = productsService.findAllById(id).get(0);
            if (p.getAmount() != 0) {
                newOrder.add(p);
                productsService.updateAmount(p.getIdProduct(), p.getAmount() - 1);
            }
        }
        Integer price =0;
        for(int i=0;i<checkedItems.size();++i){
            price += newOrder.get(i).getPrice();
        }

        Orders order = new Orders(client.getIdClient(),"Принят", new Date(), price,newOrder);
        orderService.createOrder(order);
        return "redirect:/menu.html";
    }

    @Autowired
    private OrderService orderService;

    @GetMapping("/history.html")
    public String history(Model model) {
        List<Orders> listOrders = orderService.findAllByIdClient(client.getIdClient());
        model.addAttribute("listOrders",listOrders);
        return "history.html";
    }
}

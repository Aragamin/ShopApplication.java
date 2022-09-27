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
import java.util.stream.Collectors;

@Controller
@SessionAttributes(types = Client.class)
public class ClientControllers {

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
        model.addAttribute("allProducts",allProducts);
        return "menu.html";
    }

    @PostMapping("/menu.html")
    public String addProducts(@SessionAttribute Client client, @ModelAttribute(value="addProd") AddProd listCheck, Model model){
        List<Integer> checkedItems = listCheck.getCheckedItems();
        ArrayList<Products> newOrder = new ArrayList<>();

        if(checkedItems == null || checkedItems.isEmpty()){
            return "redirect:/menu.html";//toDO выводим сообщение, что выделено 0 предметов
        }

        for (Integer id : checkedItems) {
            Products p = productsService.findAllById(id).get(0);
            if (p.getAmount() != 0) {
                newOrder.add(p);
                productsService.updateAmount(p.getIdProduct(), p.getAmount() - 1);
            }
        }
        Integer price =0;
        for(Products product : newOrder){
            price += product.getPrice();
        }

        Orders order = new Orders(client,"Принят", new Date(), price,newOrder);
        orderService.createOrder(order);
        return "redirect:/menu.html"; // todo выводим сообщение об успехе
    }

    @Autowired
    private OrderService orderService;

    @GetMapping("/history.html")
    public String history(@SessionAttribute Client client, Model model) {
        if (client != null) {
            List<Client> clients = clientService.findByLogin(client.getLogin());
            if (!clients.isEmpty()) {
                List<Orders> listOrders = clients.get(0).getOrdersClient();
                model.addAttribute("listOrders", listOrders);
                return "history.html";
            }
        } else {
            // todo сообщение об ошибке 403
            return "entry.html";
        }
        return "history.html";
    }
}

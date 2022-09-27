package com.company.shop;

import com.company.shop.Service.AdminService;
import com.company.shop.Service.ClientService;
import com.company.shop.Service.OrderService;
import com.company.shop.Service.ProductsService;
import com.company.shop.domain.Admin;
import com.company.shop.domain.Client;
import com.company.shop.domain.Orders;
import com.company.shop.domain.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class ShopApplication {

	//отмечает конструктор, поле или метод как требующий автозаполнения инъекцией зависимости Spring
	@Autowired
	private ProductsService productsService;
	@Autowired
	private ClientService clientService;
	@Autowired
	private AdminService adminService;
	@Autowired
	private OrderService orderService;

	public static void main(String[] args) {
		SpringApplication.run(ShopApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	private void test(){
//
//		добавление товара
		Products product = new Products("Маслянная краска", "синий", 10, 1200);
		productsService.createProduct(product);
		Products product1= new Products("Акварель краска", "синий", 10, 1200);
		productsService.createProduct(product1);
		Products product2 = new Products("краска", "синий", 10, 1200);
		productsService.createProduct(product2);
		for (var productFromDB : productsService.allProducts()) {
			productsService.updateAmount(productFromDB.getIdProduct(), 10);
		}
//		//вывод товара по названию
		System.out.println("вывод товара по названию");
		productsService.findTitleProduct("краска").forEach(it->System.out.println(it.printProduct()));
//
//		//вывод списка товаров
		System.out.println("вывод списка товаров");
		productsService.allProducts().forEach(it->System.out.println(it.printProduct()));
//
//		//удаление продукта по id
		productsService.deleteProduct(2);

//		//добавление клиента
		Client client = new Client("Абоба А.А.", "boris", "123", "Улица Колотушкина", "milo@mail.ru", "89379989475");
		clientService.createClient(client);
//
//
//
//		//Добавление админа
		Admin admin = new Admin("Кошелев В.В.", "admin", "admin", "88006666886");
		adminService.createAdmin(admin);
//
//		//Добавление заказа , new Date()- формирует дату сегодняшнюю
//
		ArrayList<Products> orderOne=new ArrayList<>();
		List<Products> byId1 = productsService.findAllById(68);
		if (byId1.size() > 0) {
			orderOne.add(byId1.get(0));
		}
		List<Products> byId3 = productsService.findAllById(69);
		if (byId3.size() > 0) {
			orderOne.add(byId3.get(0));
		}
		if (orderOne.size() > 1) {
			Integer price = orderOne.get(0).getPrice()+orderOne.get(1).getPrice();
			Client thirdClient = clientService.findById(3).get(0);
			Orders order = new Orders(thirdClient,"Принят", new Date(), price, orderOne);
			try {
				orderService.createOrder(order);
			} catch (Exception e) {
				System.out.println(e);
			}
		}

//		//Все заказы клиента
		System.out.println();
		clientService.findById(3).forEach(currentClient ->
			currentClient.getOrdersClient().forEach(System.out::println)
		);
//
//
//		//Изменение статуса заказа
		String status="Собран";
        orderService.updateStatus(7,status);
//
//		//Вывод номеров заказов и их статусы
		System.out.println("Номер заказа(id) и статус");
		orderService.allOrders().forEach(it->System.out.println(it.orderStatusToString()));
//
	}
}

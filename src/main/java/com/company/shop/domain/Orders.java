package com.company.shop.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
public class Orders {

    @Id
    @Column(name = "idorder")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer idOrder;

    @Column(name = "status")
    private String status;

    @Temporal(TemporalType.DATE)
    @Column(name = "dateorder")
    private Date dateOrder;

    @Column(name = "price")
    private Integer price;

    public Orders() {
    }

    public Orders(Client client, String status, Date date, Integer price, List<Products> listProducts) {
        this.client = client;
        this.status = status;
        this.dateOrder = date;
        this.price = price;
        this.listProducts = listProducts;
    }

    //связь заказов с клиентом
    @ManyToOne
    private Client client;

    //связь заказа с товарами
    @ManyToMany
    @JoinTable(
        name = "order_and_product",
        joinColumns = @JoinColumn(name = "idorder"),
        inverseJoinColumns = @JoinColumn(name = "idproduct")
    )
    List<Products> listProducts;

    public List<Products> getListProducts() {
        return listProducts;
    }

    public Integer getIdOrder() {
        return this.idOrder;
    }

    public void setListProducts(List<Products> listProducts) {
        this.listProducts = listProducts;
    }

    //Вывод заказаов
    public String printOrder() {
        return (this.status + " " + this.dateOrder + " " + this.price);
    }

    public Client getClient() {
        return client;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDate() {
        return this.dateOrder;
    }

    public void setDate(Date date) {
        this.dateOrder = date;
    }

    public Integer getPrice() {
        return this.price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }


    //вывод номера заказа и статус
    public String orderStatusToString() {
        return (this.idOrder + " " + this.status);
    }

    @Override
    public String toString() {
        //("Дата заказа" + this.dateOrder);
        StringBuilder listAllProducts = new StringBuilder();

        for (Products listProduct : this.listProducts) {
            listAllProducts.append(listProduct.getTitle()).append("\n");
        }
        System.out.println("Цена " + this.price);
        System.out.println("Статус " + this.status);
        return ("Дата заказа " + this.dateOrder + "\n" + listAllProducts + "Цена " + this.price + "\n" + "Статус " + this.status + "\n");
    }

}

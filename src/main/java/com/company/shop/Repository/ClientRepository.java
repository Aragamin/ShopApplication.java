package com.company.shop.Repository;

import com.company.shop.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client,Long> {

    //Ищем клиента по id
    List<Client> findAllByIdClient(Integer id);

    List<Client> findByLogin(String Login);

    /*@Query(value = "SELECT e from Client as e WHERE e.login=:log and e.pass=:pass", nativeQuery = true)
    Client findByPassEndLogin(String pass,String log);*/
}

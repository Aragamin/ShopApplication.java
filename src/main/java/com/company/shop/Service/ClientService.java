package com.company.shop.Service;

import com.company.shop.Repository.ClientRepository;
import com.company.shop.domain.Client;
import com.sun.istack.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {
    @Autowired
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public boolean createClient(Client client) {
        List<Client> clientFromBD = clientRepository.findByLogin(client.getLogin());

        //если логин занят, возвращаем ложь
        if (clientFromBD != null && !clientFromBD.isEmpty()) {
            return false;
        }
        clientRepository.save(client);
        return true;
    }

    //Ищем клиента по  id (пока не нужно)
    public List<Client> findById(Integer id) {
        return clientRepository.findAllByIdClient(id);
    }

    public List<Client> findByLogin(String log) {
        return clientRepository.findByLogin(log);
    }

    @Nullable
    public Client findByPassAndLogin(String log, String psw) {
        List<Client> clientFromBD = clientRepository.findByLogin(log);
        if (clientFromBD.size() > 0 && psw.equals(clientFromBD.get(0).getPass())) {
            return clientFromBD.get(0);
        }
        return null;
    }
}

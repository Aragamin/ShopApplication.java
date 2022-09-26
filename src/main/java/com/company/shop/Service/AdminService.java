package com.company.shop.Service;

import com.company.shop.Repository.AdminRepository;
import com.company.shop.domain.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public void  createAdmin(Admin admin){
        adminRepository.save(admin);
    }

    public boolean findByPassAndLogin(String log, String psw){
        List<Admin> clientFromBD = adminRepository.findByLogin(log);
        return clientFromBD.size() > 0 && psw.equals(clientFromBD.get(0).getPass());
    }
}

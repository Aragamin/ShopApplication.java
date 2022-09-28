package com.company.shop.Service;

import com.company.shop.Repository.AdminRepository;
import com.company.shop.domain.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
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

    @Nullable
    public Admin findByPassAndLogin(String log, String psw){
        List<Admin> adminFromBD = adminRepository.findByLogin(log);
        for (Admin admin : adminFromBD) {
            if (admin.getPass().equals(psw)) {
                return admin;
            }
        }
        return null;
    }
}

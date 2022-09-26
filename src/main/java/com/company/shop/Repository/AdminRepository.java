package com.company.shop.Repository;

import com.company.shop.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminRepository extends JpaRepository<Admin,Long> {
    List<Admin> findByLogin(String Login);
}

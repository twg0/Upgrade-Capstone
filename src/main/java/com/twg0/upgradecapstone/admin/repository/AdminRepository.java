package com.twg0.upgradecapstone.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.twg0.upgradecapstone.admin.domain.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long>, CustomAdminRepository {

}

package com.bookstore.auth.repo;

import com.bookstore.auth.model.jpa.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role , Long> {
}

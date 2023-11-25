package com.example.neobis.repository;

import com.example.neobis.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<Role, Long> {
   Optional<Role> findRoleByName(String name);
}

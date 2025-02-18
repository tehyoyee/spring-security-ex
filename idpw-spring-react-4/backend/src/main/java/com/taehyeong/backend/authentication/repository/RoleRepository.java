package com.taehyeong.backend.authentication.repository;

import com.taehyeong.backend.authentication.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}

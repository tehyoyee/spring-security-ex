package com.taehyeong.backend.authentication.repository;

import com.taehyeong.backend.authentication.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {

    Set<Authority> findByRoleId(Integer roleId);

}

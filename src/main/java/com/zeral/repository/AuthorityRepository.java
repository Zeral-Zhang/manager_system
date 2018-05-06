package com.zeral.repository;

import com.zeral.domain.Role;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Role entity.
 */
public interface AuthorityRepository extends JpaRepository<Role, String> {
}

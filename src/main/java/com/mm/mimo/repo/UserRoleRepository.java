package com.mm.mimo.repo;

import com.mm.mimo.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, UserRole.PK> {
}

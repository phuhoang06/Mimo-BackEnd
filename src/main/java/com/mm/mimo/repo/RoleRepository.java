package com.mm.mimo.repo;

import com.mm.mimo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import com.mm.mimo.enums.RoleEnum;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(RoleEnum name);
}

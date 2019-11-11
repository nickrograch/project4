package com.javamentors.repository;

import com.javamentors.entity.AppUser;
import com.javamentors.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<AppUser, Long> {
    @Query("select a from Role a where a.role = :role")
    Role findByName(@Param("role") String role);
}


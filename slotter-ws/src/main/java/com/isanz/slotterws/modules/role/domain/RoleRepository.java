package com.isanz.slotterws.modules.role.domain;

import com.isanz.slotterws.modules.users.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByName(String name);

    List<Role> findAllByUsersIn(Collection<User> users);}

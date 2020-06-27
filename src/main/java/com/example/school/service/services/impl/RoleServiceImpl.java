package com.example.school.service.services.impl;

import com.example.school.data.entities.Role;
import com.example.school.data.repositories.RoleRepository;
import com.example.school.service.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    private void seedRoles() {
        if (roleRepository.findAll().isEmpty()) {
            roleRepository.saveAll(
                    new ArrayList<>() {{
                        add(new Role("TEACHER"));
                        add(new Role("STUDENT"));
                    }});
        }
    }

    @Override
    public Role getRole(String role) {
        return roleRepository
                .findAll()
                .stream()
                .filter(r -> r.getAuthority().toLowerCase().contains(role.toLowerCase()))
                .findFirst()
                .orElse(new Role("STUDENT"));
    }
}

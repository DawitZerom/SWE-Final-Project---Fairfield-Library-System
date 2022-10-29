package edu.miu.cs.cs425.fairfieldlibrarywebapp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.miu.cs.cs425.fairfieldlibrarywebapp.model.Role;
import edu.miu.cs.cs425.fairfieldlibrarywebapp.repository.RoleRepository;
import edu.miu.cs.cs425.fairfieldlibrarywebapp.service.RoleService;
import org.springframework.data.domain.Sort;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll(Sort.by("name"));
    }

}

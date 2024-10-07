package com.novi.mappers;

import com.novi.dtos.AdminInputDTO;
import com.novi.dtos.AdminOutputDTO;
import com.novi.entities.Admin;
import org.springframework.stereotype.Component;

@Component
public class AdminMapper {

    public AdminOutputDTO toAdminOutputDTO(Admin admin) {
        AdminOutputDTO dto = new AdminOutputDTO();
        dto.setId(admin.getId());
        dto.setEmail(admin.getEmail());
        dto.setLastLogin(admin.getLastLogin());
        return dto;
    }

    public Admin toAdminEntity(AdminInputDTO adminInputDTO) {
        Admin admin = new Admin();
        admin.setEmail(adminInputDTO.getEmail());
        admin.setPassword(adminInputDTO.getPassword());
        return admin;
    }
}


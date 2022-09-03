package com.bridgelabz.lmsadminservice.service;

import com.bridgelabz.lmsadminservice.dto.AdminDto;
import com.bridgelabz.lmsadminservice.model.AdminModel;
import com.bridgelabz.lmsadminservice.util.Response;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IAdminService {
    AdminModel addAdmin(AdminDto adminDto);

    AdminModel changePassword(String token, String password);

    Response login(String email, String password);

    List<AdminModel> getAdminData(String token);

    AdminModel resetPassword(String emailId);

    AdminModel updateAdmin(long id, String token, AdminDto adminDto);

    AdminModel getDeleteAdmin(long id, String token);
}
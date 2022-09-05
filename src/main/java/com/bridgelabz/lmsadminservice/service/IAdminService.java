package com.bridgelabz.lmsadminservice.service;

import com.bridgelabz.lmsadminservice.dto.AdminDto;
import com.bridgelabz.lmsadminservice.model.AdminModel;
import com.bridgelabz.lmsadminservice.util.Response;
import com.bridgelabz.lmsadminservice.util.ResponseToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IAdminService {
    Response addAdmin(AdminDto adminDto);

    Response changePassword(String token, String password);

    ResponseToken login(String email, String password);

    List<AdminModel> getAdminData(String token);

    Response resetPassword(String emailId);

    Response updateAdmin(long id, String token, AdminDto adminDto);

    Response deleteAdmin(long id, String token);

    Boolean validate(String token);
}
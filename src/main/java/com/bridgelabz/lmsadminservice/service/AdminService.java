package com.bridgelabz.lmsadminservice.service;

import com.bridgelabz.lmsadminservice.dto.AdminDto;
import com.bridgelabz.lmsadminservice.exception.AdminNotFoundException;
import com.bridgelabz.lmsadminservice.model.AdminModel;
import com.bridgelabz.lmsadminservice.repository.AdminRepository;
import com.bridgelabz.lmsadminservice.util.Response;
import com.bridgelabz.lmsadminservice.util.ResponseToken;
import com.bridgelabz.lmsadminservice.util.TokenUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService implements IAdminService{
    @Autowired
    AdminRepository adminRepository;
    @Autowired
    TokenUtil tokenUtil;
    @Autowired
    MailService mailService;
    @Override
    public Response addAdmin(AdminDto adminDto) {
        AdminModel adminModel = new AdminModel(adminDto);
        adminModel.setCreatorStamp(LocalDateTime.now());
        adminRepository.save(adminModel);
        String body = "Admin added: " +adminModel.getId();
        String subject = "Admin registration successfully";
        mailService.send(adminModel.getEmailId(), body, subject);
        return new Response("Success", 200, adminModel);
    }

    @Override
    public Response updateAdmin(long id, String token, AdminDto adminDto) {
            Long userId = tokenUtil.decodeToken(token);
            Optional<AdminModel> isAdminPresent = adminRepository.findById(userId);
            if(isAdminPresent.isPresent()){
                isAdminPresent.get().setFirstName(adminDto.getFirstName());
                isAdminPresent.get().setLastName(adminDto.getLastName());
                isAdminPresent.get().setMobile(adminDto.getMobile());
                isAdminPresent.get().setEmailId(adminDto.getEmailId());
                isAdminPresent.get().setProfilePath(adminDto.getProfilePath());
                isAdminPresent.get().setStatus(adminDto.getStatus());
                adminRepository.save(isAdminPresent.get());
                return new Response("Success", 200, isAdminPresent.get());
            }
            throw new AdminNotFoundException(400, "Admin Not Present!!!");
        }

    @Override
    public List<AdminModel> getAdminData(String token) {
        List<AdminModel> getalladmindata = adminRepository.findAll();
        if(getalladmindata.size() > 0){
            return getalladmindata;
        }
        throw new AdminNotFoundException(400, "Admin Not Found");
    }

    @Override
    public Response resetPassword(String emailId) {
        Optional<AdminModel> isEmailPresent = adminRepository.findByEmailId(emailId);
        if(isEmailPresent.isPresent()){
            String token = tokenUtil.createToken(isEmailPresent.get().getId());
            String url = "http://localhost:8082/admin/changePassword";
            String subject = "reset password";
            String body = "For reset password click this link" +url+"use this to reset"+token;
            mailService.send(isEmailPresent.get().getEmailId(), body, subject);
        }
        return new Response("Success", 200, isEmailPresent.get());
    }

    @Override
    public Response changePassword(String token, String password) {
        Long id = tokenUtil.decodeToken(token);
        Optional<AdminModel> isIdPresent = adminRepository.findById(id);
        if(isIdPresent.isPresent()){
            isIdPresent.get().setPassword(password);
            adminRepository.save(isIdPresent.get());
            return new Response("Success", 200, isIdPresent.get());
        }
        else{
            throw new AdminNotFoundException(400, "Password is worng");
        }
    }


    @Override
    public Response deleteAdmin(long id, String token) {
        Long userId = tokenUtil.decodeToken(token);
        Optional<AdminModel> isAdminPresent = adminRepository.findById(userId);
        if(isAdminPresent.isPresent()){
            adminRepository.save(isAdminPresent.get());
            String body = "Admin deleted: " +isAdminPresent.get().getId();
            String subject = "Admin deleted successfully";
            mailService.send(isAdminPresent.get().getEmailId(), body, subject);
            return new Response("Success", 200, isAdminPresent.get());
        }
        throw new AdminNotFoundException(400, "Admin does not found");
    }

    @Override
    public ResponseToken login(String email, String password) {
            Optional<AdminModel> isAdminPresent = adminRepository.findByEmailId(email);
            if(isAdminPresent.isPresent()) {
                if (isAdminPresent.get().getPassword().equals(password)) {
                    String token = tokenUtil.createToken(isAdminPresent.get().getId());
                    return new ResponseToken(200, "success", token);
                }
                throw new AdminNotFoundException(400, "Invalid Credentials");
            }
            throw new AdminNotFoundException(400, "Admin Not Found");
        }
    @Override
    public Boolean validate(String token){
        Long userId = tokenUtil.decodeToken(token);
        Optional<AdminModel> isAdmin = adminRepository.findById(userId);
        if (isAdmin.isPresent()){
            return true;
        }
        return false;
    }

}
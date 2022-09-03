package com.bridgelabz.lmsadminservice.service;

import com.bridgelabz.lmsadminservice.dto.AdminDto;
import com.bridgelabz.lmsadminservice.exception.AdminNotFoundException;
import com.bridgelabz.lmsadminservice.model.AdminModel;
import com.bridgelabz.lmsadminservice.repository.AdminRepository;
import com.bridgelabz.lmsadminservice.util.Response;
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
    public AdminModel addAdmin(AdminDto adminDto) {
        AdminModel adminModel = new AdminModel(adminDto);
        adminModel.setCreatorStamp(LocalDateTime.now());
        adminRepository.save(adminModel);
        String body = "Admin added: " +adminModel.getId();
        String subject = "Admin registration successfully";
        mailService.send(adminModel.getEmailId(), body, subject);
        return adminModel;
    }

    @Override
    public AdminModel updateAdmin(long id, String token, AdminDto adminDto) {
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
                return isAdminPresent.get();
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
    public AdminModel resetPassword(String emailId) {
        Optional<AdminModel> isEmailPresent = adminRepository.findByEmailId(emailId);
        if(isEmailPresent.isPresent()){
            String token = tokenUtil.createToken(isEmailPresent.get().getId());
            String url = "http://localhost:8081/admin/changePassword";
            String subject = "reset password";
            String body = "For reset password click this link" +url+"use this to reset"+token;
            mailService.send(isEmailPresent.get().getEmailId(), body, subject);
        }
        return isEmailPresent.get();
    }

    @Override
    public AdminModel changePassword(String token, String password) {
        Long id = tokenUtil.decodeToken(token);
        Optional<AdminModel> isIdPresent = adminRepository.findById(id);
        if(isIdPresent.isPresent()){
            isIdPresent.get().setPassword(password);
            adminRepository.save(isIdPresent.get());
            return isIdPresent.get();
        }
        else{
            throw new AdminNotFoundException(400, "Password is worng");
        }
    }


    @Override
    public AdminModel getDeleteAdmin(long id, String token) {
        Long userId = tokenUtil.decodeToken(token);
        Optional<AdminModel> isAdminPresent = adminRepository.findById(userId);
        if(isAdminPresent.isPresent()){
            adminRepository.save(isAdminPresent.get());
            String body = "Admin deleted: " +isAdminPresent.get().getId();
            String subject = "Admin deleted successfully";
            mailService.send(isAdminPresent.get().getEmailId(), body, subject);
            return isAdminPresent.get();
        }
        throw new AdminNotFoundException(400, "Admin does not found");
    }

    @Override
    public Response login(String email, String password) {
            Optional<AdminModel> isAdminPresent = adminRepository.findByEmailId(email);
            if(isAdminPresent.isPresent()) {
                if (isAdminPresent.get().getPassword().equals(password)) {
                    String token = tokenUtil.createToken(isAdminPresent.get().getId());
                    return new Response("login successful", 200, token);
                }
                throw new AdminNotFoundException(400, "Invalid Credentials");
            }
            throw new AdminNotFoundException(400, "Admin Not Found");
        }

}
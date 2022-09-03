package com.bridgelabz.lmsadminservice.controller;

import com.bridgelabz.lmsadminservice.dto.AdminDto;
import com.bridgelabz.lmsadminservice.model.AdminModel;
import com.bridgelabz.lmsadminservice.service.IAdminService;
import com.bridgelabz.lmsadminservice.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    IAdminService adminService;

    @PostMapping("/addadmin")
    public AdminModel addAdmin(@RequestBody AdminDto adminDto){
        return adminService.addAdmin(adminDto);
    }

    @PutMapping("/update/{id}")
    public AdminModel updateAdmin(@RequestHeader String token, @RequestBody AdminDto adminDto, @PathVariable long id){
        return adminService.updateAdmin(id, token, adminDto);
    }

    @GetMapping("/getadmindata")
    public List<AdminModel> getAllAdmin(@RequestHeader String token){
        return adminService.getAdminData(token);
    }

    @DeleteMapping("/deleteadmin")
    public AdminModel deleteAdmin(@PathVariable long id, @RequestHeader String token){
        return adminService.getDeleteAdmin(id, token);
    }

    @PostMapping("/login")
    public Response login(@RequestParam String email, @RequestParam String password){
        return adminService.login(email, password);
    }

    @PutMapping("/changepassword")
    public AdminModel changePassword(@RequestHeader String token, @RequestParam String password){
        return adminService.changePassword(token, password);

    }

    @PutMapping("/resetpassword")
    public AdminModel resetPassword(@RequestParam String emailId){
        return adminService.resetPassword(emailId);
    }
}

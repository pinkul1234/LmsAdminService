package com.bridgelabz.lmsadminservice.controller;

import com.bridgelabz.lmsadminservice.dto.AdminDto;
import com.bridgelabz.lmsadminservice.model.AdminModel;
import com.bridgelabz.lmsadminservice.service.IAdminService;
import com.bridgelabz.lmsadminservice.util.Response;
import com.bridgelabz.lmsadminservice.util.ResponseToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    IAdminService adminService;

    @PostMapping("/addadmin")
    public ResponseEntity<Response> addAdmin(@RequestBody AdminDto adminDto){
        Response response = adminService.addAdmin(adminDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Response> updateAdmin(@RequestHeader String token, @RequestBody AdminDto adminDto, @PathVariable long id){
        Response response = adminService.updateAdmin(id, token, adminDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getadmindata")
    public ResponseEntity<List<?>> getAllAdmin(@RequestHeader String token){
        List<AdminModel> response = adminService.getAdminData(token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/deleteadmin")
    public ResponseEntity<Response> deleteAdmin(@PathVariable long id, @RequestHeader String token){
        Response response = adminService.deleteAdmin(id, token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseToken> login(@RequestParam String email, @RequestParam String password){
        ResponseToken responseToken = adminService.login(email, password);
        return new ResponseEntity<>(responseToken, HttpStatus.OK);
    }

    @PutMapping("/changepassword")
    public ResponseEntity<Response> changePassword(@RequestHeader String token, @RequestParam String password){
        Response response = adminService.changePassword(token, password);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PutMapping("/resetpassword")
    public ResponseEntity<Response> resetPassword(@RequestParam String emailId){
        Response response = adminService.resetPassword(emailId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/validate/{token}")
    public Boolean validate(@PathVariable String token){
        return adminService.validate(token);
    }
}

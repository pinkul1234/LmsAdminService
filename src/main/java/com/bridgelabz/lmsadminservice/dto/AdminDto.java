package com.bridgelabz.lmsadminservice.dto;

import lombok.Data;

@Data
public class AdminDto {
    private String firstName;
    private String lastName;
    private String mobile;
    private String emailId;
    private String profilePath;
    private String status;
    private String password;
    private String creatorUseName;
    private String updatedUserName;
}
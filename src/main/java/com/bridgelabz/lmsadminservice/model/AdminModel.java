package com.bridgelabz.lmsadminservice.model;
import com.bridgelabz.lmsadminservice.dto.AdminDto;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Admin")
@Data
public class AdminModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private String mobile;
    private String emailId;
    private String profilePath;
    private String status;
    private String password;
    private LocalDateTime creatorStamp;
    private LocalDateTime updatedStamp;
    private String creatorUserName;
    private String updatedUserName;



    public AdminModel(AdminDto adminDto){
        this.firstName = adminDto.getFirstName();
        this.lastName = adminDto.getLastName();
        this.emailId = adminDto.getEmailId();
        this.mobile = adminDto.getMobile();
        this.profilePath = adminDto.getProfilePath();
        this.status = adminDto.getStatus();
        this.password = adminDto.getPassword();
        this.creatorUserName = adminDto.getCreatorUseName();
        this.updatedUserName = adminDto.getUpdatedUserName();

    }

    public AdminModel() {

    }

}

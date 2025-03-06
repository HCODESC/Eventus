package com.hcodes.eventus.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Entity
@Table(name = "users")
@Data
public class UserEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id; 

    @NotBlank(message = "Username is required")
    @Column(unique = true, nullable = false, length = 50)
    public String username; 
    @NotBlank(message = "Password is required")
    @Column(nullable = false, length = 255)
    public String password; 

    @NotBlank(message = "First name is required")
    @Column(nullable = false, length = 100)
    public String firstName; 
    
    @NotBlank(message = "Last name is required")
    @Column(nullable = false, length = 100)
    public String lastName; 


    @Email
    @NotBlank(message = "Email is required")
    @Column(unique = true, nullable = false, length = 100)
    public String email;

    public Role role; 

    public enum Role {
        CUSTOMER, ADMIN;
    }

}

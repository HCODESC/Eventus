/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.hcodes.eventus.dto;

import lombok.Data;

/**
 *
 * @author mavis
 */

@Data
public class LoginDto {

    public String username; 
    public String Password; 
    public String email; 

    public LoginDto(String username, String Password) {
        this.username = username;
        this.Password = Password;
    }
}

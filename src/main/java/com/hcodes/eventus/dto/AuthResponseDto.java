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
 public class AuthResponseDto {


    private String accessToken;
    private String tokenType = "Bearer";

    public AuthResponseDto(String accessToken) {
        this.accessToken = accessToken;
    }
}


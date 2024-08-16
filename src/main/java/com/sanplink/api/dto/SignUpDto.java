package com.sanplink.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDto {
    private Long id;
    private String username;
    private String email;
    private String password;
    private Date createAt;
    private Date updateAt;
}

package com.example.plant_lovers.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class UserDTO {
    private Integer id;
    private String uLogin;
    private String uName;
    private String uPassword;
}

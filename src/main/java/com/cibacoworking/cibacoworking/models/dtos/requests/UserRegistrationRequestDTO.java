
package com.cibacoworking.cibacoworking.models.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserRegistrationRequestDTO {
    private String name;
    private String email;
    private String phone;
    private String projectName;
    private String password;
}

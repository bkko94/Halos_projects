package com.mysite.askAnything.admin;

import com.mysite.askAnything.user.UserRole;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
public class ModifyForm {


    private String email;

    @Enumerated(EnumType.STRING)
    private UserRole role;

}

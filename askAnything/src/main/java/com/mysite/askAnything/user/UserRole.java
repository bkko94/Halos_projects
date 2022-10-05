package com.mysite.askAnything.user;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER"),
    MANAGER("ROLE_MANAGER"),

    GUEST("ROLE_GUEST");

    UserRole(String value){
        this.value = value;
    }

    private String value;
}

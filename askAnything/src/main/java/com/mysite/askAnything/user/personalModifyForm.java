package com.mysite.askAnything.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
public class personalModifyForm {
    private String email;

    private String passwordcheck;


    /*@Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W),(>=\\S+$).{8,16}",
            message = "비밀번호는 8~16자 영문 대소문자, 숫자, 특수문자를 포함하여 입력하세요")*/

    private String newpassword1;


    private String newpassword2;
}

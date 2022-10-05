package com.mysite.askAnything.user;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserCreateForm {
    //@Size(min = 3, max = 25)
    @Pattern(regexp = "^[a-z0-9]{5,20}", message = "아이디는 영어소문자 또는 숫자를 사용해 5~20자를 입력하세요")
    @NotEmpty(message="사용자 ID는 필수항목입니다.")
    private String username;


    /*에러나서 확인요망*/
  /* @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W),(?=\\S+$).{8,16}",
           message = "비밀번호는 8~16자 영문 대소문자, 숫자, 특수문자를 포함하여 입력하세요")
*/

    @NotEmpty(message="비밀번호는 필수항목입니다.")
    private String password1;

    @NotEmpty(message="비밀번호 확인은 필수항목입니다.")
    private String password2;

    @NotEmpty(message="이메일은 필수항목입니다.")
    @Email
    private String email;

    @Enumerated(EnumType.STRING)
    private UserRole role;
}

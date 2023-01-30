package com.mysite.askAnything.user;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@DynamicInsert
public class SiteUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    @Column(unique = true)
    private String email;


    @Enumerated(EnumType.STRING)
    private UserRole role;

    private boolean enabled; /*계정 활성화 */

    private LocalDateTime createDate;
    
    System.out.println("수정수정");

}

package com.mysite.askAnything.comment;

import com.mysite.askAnything.post.Post;
import com.mysite.askAnything.user.SiteUser;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @ManyToOne
    private Post post;

    @ManyToOne(cascade = CascadeType.REMOVE)
    private SiteUser author;


    private LocalDateTime modifyDate;

}

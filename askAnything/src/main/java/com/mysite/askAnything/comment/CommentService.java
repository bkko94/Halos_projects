package com.mysite.askAnything.comment;

import com.mysite.askAnything.DataNotFoundException;
import com.mysite.askAnything.post.Post;
import com.mysite.askAnything.user.SiteUser;
import com.mysite.askAnything.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.expression.spel.ast.OpInc;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public Comment create(Post post, String content, SiteUser author){
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setCreateDate(LocalDateTime.now());
        comment.setPost(post);
        comment.setAuthor(author);
        this.commentRepository.save(comment);
        return comment;
    }

    public Comment getComment(Integer id){
        Optional<Comment> comment = this.commentRepository.findById(id);
        if (comment.isPresent()){
            return comment.get();
        }else{
            throw new DataNotFoundException("comment not found");
        }
    }

    public void modify(Comment comment, String content){
        comment.setContent(content);
        comment.setModifyDate(LocalDateTime.now());
        this.commentRepository.save(comment);
    }

    public void delete(Comment comment){
        this.commentRepository.delete(comment);
    }


}

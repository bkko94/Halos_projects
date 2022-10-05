package com.mysite.askAnything.comment;

import com.mysite.askAnything.post.Post;
import com.mysite.askAnything.post.PostService;
import com.mysite.askAnything.user.SiteUser;
import com.mysite.askAnything.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/comment")
@Slf4j
@RequiredArgsConstructor
public class CommentController {
    private final PostService postService;
    private final CommentService commentService;
    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
   public String createComment(Model model, @PathVariable("id") Integer id,
                              @Valid CommentForm commentForm, BindingResult bindingResult, Principal principal){
       Post post = this.postService.getPost(id);
       SiteUser siteUser = this.userService.getUser(principal.getName());
       if(bindingResult.hasErrors()){
           model.addAttribute("post", post);
           return "post_detail";
       }
       Comment comment = this.commentService.create(post, commentForm.getContent(), siteUser);
        log.info("사용자 [{}]님이 댓글 : '{}'을 작성했습니다.",principal.getName(),commentForm.getContent());
       return String.format("redirect:/post/detail/%s#comment_%s",
               comment.getPost().getId(), comment.getId(), comment.getId());

   }



    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @GetMapping("/modify/{id}")
    public String commentModify(CommentForm commentForm, @PathVariable("id") Integer id, Principal principal) {
        Comment comment = this.commentService.getComment(id);
     /*   if (!comment.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }

      */
        commentForm.setContent(comment.getContent());
        return "comment_form";
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @PostMapping("/modify/{id}")
    public String commentModify(@Valid CommentForm commentForm, BindingResult bindingResult,
                                @PathVariable("id") Integer id, Principal principal){
        if(bindingResult.hasErrors()){
            return "comment_form";
        }
       Comment comment = this.commentService.getComment(id);
        log.info("사용자 [{}] 가 댓글 : '{}'을 수정했습니다.",principal.getName(),commentForm.getContent());
      /*   if(!comment.getAuthor().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정권한이 없습니다.");
        }

     */
        this.commentService.modify(comment, commentForm.getContent());
        return String.format("redirect:/post/detail/%s#comment_%s", comment.getPost().getId(), comment.getId());
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/delete/{id}")
    public String commentDelete(Principal principal, @PathVariable("id") Integer id){
        Comment comment = this.commentService.getComment(id);
    /*    if(!comment.getAuthor().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"삭제권한이 없습니다.");
        }
    */
        this.commentService.delete(comment);
        log.info("사용자 [{}]가 댓글 '{}'을 삭제했습니다.", principal.getName(), comment.getContent());
        return String.format("redirect:/post/detail/%s", comment.getPost().getId());
    }



}

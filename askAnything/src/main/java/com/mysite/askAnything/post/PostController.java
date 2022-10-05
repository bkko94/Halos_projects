package com.mysite.askAnything.post;

import com.mysite.askAnything.comment.Comment;
import com.mysite.askAnything.comment.CommentForm;
import com.mysite.askAnything.user.SiteUser;
import com.mysite.askAnything.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import javax.persistence.criteria.*;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RequestMapping("/post")
@Slf4j
@RequiredArgsConstructor
@Controller
public class PostController {
    private final PostService postService;
    private final UserService userService;

    @RequestMapping("/list")
    public String list(Model model, @RequestParam(value="page", defaultValue ="0") int page,
                       @RequestParam(value="kw", defaultValue = "") String kw, Principal principal){

        Page<Post> paging = this.postService.getList(page, kw);
        model.addAttribute("paging", paging);
        model.addAttribute("kw", kw);
        model.addAttribute("user", principal.getName());
        log.info("사용자 [{}], 게시판 {} 페이지 열람",principal.getName(),page);
        log.info("키워드 '{}' 검색", kw);
        return "post_list";
    }


    @RequestMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id, CommentForm commentForm, Principal principal){
        Post post = this.postService.getPost(id);
        model.addAttribute("post", post);
        model.addAttribute("user", principal.getName());
        log.info("사용자 [{}]님이 게시글 '{}'을 열람하였습니다.", principal.getName(), post.getSubject());
        return "post_detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String postCreate(PostForm postForm , Model model, Principal principal){

        model.addAttribute("user", principal.getName());
        return "post_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String postCreate(@Valid PostForm postForm, BindingResult bindingResult, Principal principal){
        //TODO 질문을 저장한다.

        if (bindingResult.hasErrors()){
            return "post_form";
        }
        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.postService.create(postForm.getSubject(), postForm.getContent(), siteUser);
        log.info("사용자 [{}]가 게시글 '{}'을(를) 작성하였습니다.",principal.getName(),postForm.getSubject());
        return "redirect:/post/list"; //질문 저장후 질문목록으로 이동
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/modify/{id}")
    public String postModify(Model model,PostForm postForm, @PathVariable("id") Integer id, Principal principal){
  /*{

        if(!post.getAuthor().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
       }*/
        Post post = this.postService.getPost(id);
        postForm.setSubject(post.getSubject());
        postForm.setContent(post.getContent());
        model.addAttribute("user", principal.getName());
        return "post_form";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/modify/{id}")
    public String postModify(@Valid PostForm postForm, BindingResult bindingResult,
                             Principal principal, @PathVariable("id") Integer id){
        if (bindingResult.hasErrors()){
            return "post_form";
        }
        Post post = this.postService.getPost(id);
       // if (!post.getAuthor().getUsername().equals(principal.getName())){
       //     throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정권한이 없습니다.");
       // }
        this.postService.modify(post, postForm.getSubject(), postForm.getContent());
        log.info("사용자[{}]가 게시글 '{}'을(를) 수정했습니다.",principal.getName(), postForm.getSubject());
        return String.format("redirect:/post/detail/%s", id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/delete/{id}")
    public String postDelete(Principal principal, @PathVariable("id") Integer id){
        Post post = this.postService.getPost(id);
       // if (!post.getAuthor().getUsername().equals(principal.getName())){
       //     throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
    //}
        this.postService.delete(post);
        log.info("사용자[{}]가 게시글 '{}'을(를) 삭제했습니다.",principal.getName(), post.getSubject());
        return "redirect:/post/list";
    }

    private Specification<Post> search(String kw) {
        return new Specification<Post>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Post> p, CriteriaQuery<?> query, CriteriaBuilder cb){
                query.distinct(true); //중복 제거
                Join<Post, SiteUser> u1 = p.join("author", JoinType.LEFT);
                Join<Post, Comment> a = p.join("commentList", JoinType.LEFT);
                Join<Comment, SiteUser> u2 = a.join("author", JoinType.LEFT);

                return cb.or(cb.like(p.get("subject"), "%" + kw + "%" ),
                        cb.like(p.get("content"), "%" + kw + "%"),
                        cb.like(u1.get("username"), "%" + kw + "%"),
                        cb.like(a.get("content"), "%" + kw +"%"),
                        cb.like(u2.get("username"), "%"+  kw + "%"));

            }

        };
    }

}

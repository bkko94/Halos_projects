package com.mysite.askAnything.user;

import com.mysite.askAnything.comment.CommentForm;
import com.mysite.askAnything.post.Post;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/user")
public class UserController {
   @Autowired
   PasswordEncoder passwordEncoder;

 //   @Autowired
 //  private final RequestService requestService;

    private final UserService userService;

    @GetMapping("/signup")
    public String signup(UserCreateForm userCreateForm) {

        return "signup_form";
    }


    @PostMapping("/signup")
    public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "signup_form";
        }

        if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordIncorrect",
                    "2개의 패스워드가 일치하지않습니다.");
            return "signup_form";
        }

       /* if (!userCreateForm.getEmail().equals(siteUser.getEmail())){
            bindingResult.rejectValue("email","Emailoverlap",
                    "이미 사용된 이메일입니다.");
            return "signup_form";
        }*/

        try {
            userService.create(userCreateForm.getUsername(),
                    userCreateForm.getEmail(), userCreateForm.getPassword1(), userCreateForm.getRole());
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "signup_form";
        } catch (Exception e) {
            e.printStackTrace();

            bindingResult.rejectValue("signupFailed", e.getMessage());
            return "signup_form";
        }
        log.info("사용자 {}님이 새로 가입하였습니다.", userCreateForm.getUsername());
        return "redirect:/";
    }


    @GetMapping("/login")
    public String login() {
        return "login_form";
    }

    @GetMapping("/main")
    public String main(Model model, Principal principal) {



        model.addAttribute("user", principal.getName());
    //   log.info("접속자 IP:{}", requestService.getClientIp(request));
        return "main_page";
    }




    @GetMapping("/modify")
    public String userModify(Model model, @Valid personalModifyForm modifyForm, Principal principal, SiteUser siteUser) {

        model.addAttribute("user", principal.getName());
        return "pwd_modify_form";
    }



    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify")
    public String userModify(Model model, @Valid personalModifyForm modifyForm, BindingResult bindingResult,
                             Principal principal, SiteUser siteUser) {

        model.addAttribute("user", principal.getName());

        if (bindingResult.hasErrors()) {
            return "pwd_modify_form";
        }


        if (!passwordEncoder.matches(modifyForm.getPasswordcheck(),siteUser.getPassword())){
            bindingResult.rejectValue("passwordcheck", "passwordIncorrect",
                    "기존 비밀번호와 일치하지 않습니다.");
            return "pwd_modify_form";
        }

        if (modifyForm.getPasswordcheck().equals(modifyForm.getNewpassword1())) {
            bindingResult.rejectValue("passwordcheck", "passwordoverlap",
                    "보안을 위해 새로운 비밀번호를 입력하세요.");
            return "pwd_modify_form";
        }

        if (!modifyForm.getNewpassword1().equals(modifyForm.getNewpassword2())) {
            bindingResult.rejectValue("password2", "passwordIncorrect",
                    "2개의 패스워드가 일치하지않습니다.");
            return "pwd_modify_form";
        }

        try {
            userService.modify(siteUser, modifyForm.getEmail(), modifyForm.getNewpassword1());
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("modifyFailed", "이미 등록된 사용자입니다.");
            return "pwd_modify_form";
        } catch (Exception e) {
            e.printStackTrace();
            ;
            bindingResult.rejectValue("modifyFailed", e.getMessage());
            return "pwd_modify_form";
        }
        log.info("[{}]님이 정보를 수정하였습니다.", principal.getName());
        return String.format("redirect:/user/main/");
    }







}


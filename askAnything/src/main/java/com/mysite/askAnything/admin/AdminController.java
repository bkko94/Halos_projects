package com.mysite.askAnything.admin;

import com.mysite.askAnything.admin.ModifyForm;
import com.mysite.askAnything.user.SiteUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    PasswordEncoder passwordEncoder;

    private final AdminService adminService;

    @RequestMapping("/list")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page, Principal principal) {
        Page<SiteUser> paging = this.adminService.getList(page);
        model.addAttribute("paging", paging);
        model.addAttribute("user", principal.getName());

        return "user_list";
    }

    @GetMapping("/modify/{id}")
    public String userModify(Model model, ModifyForm modifyForm, @PathVariable("id") long id, Principal principal) {
        SiteUser siteUser = this.adminService.getmodUser(id);
       /* modifyForm.setEmail(siteUser.getEmail());
        modifyForm.setRole(siteUser.getRole());*/
        model.addAttribute("user", principal.getName());
        model.addAttribute("modusername", siteUser.getUsername());
        return "modify_form";
    }


    /*@PreAuthorize("hasRole('ADMIN')")*/
    @PutMapping("/modify/{id}")
    public String userModify(Model model, @Valid @RequestBody ModifyForm modifyForm, BindingResult bindingResult,
                             Principal principal, @PathVariable("id") long id) {

        SiteUser siteUser = this.adminService.getmodUser(id);
        model.addAttribute("user", principal.getName());
        model.addAttribute("modusername", siteUser.getUsername());
        model.addAttribute("role", siteUser.getRole());

        if (bindingResult.hasErrors()) {
            return "modify_form";
        }

        try {
            adminService.modify(siteUser, modifyForm.getEmail(), modifyForm.getRole());
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("modifyFailed", "이미 등록된 사용자입니다.");
            return "modify_form";
        } catch (Exception e) {
            e.printStackTrace();
            bindingResult.rejectValue("modifyFailed", e.getMessage());
            return "modify_form";
        }
        log.info("관리자 {}님이 회원 [{}]님의 정보를 수정하였습니다.", principal.getName(), siteUser.getUsername());
        return String.format("redirect:/admin/list/");
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/delete/{id}")
    public String delete(Principal principal, @PathVariable("id") long id){
        SiteUser siteUser = this.adminService.getmodUser(id);
        this.adminService.delete(id);
        log.info("관리자[{}]가 회원 '{}'을(를) 삭제했습니다.",principal.getName(), siteUser.getUsername());
        return String.format("redirect:/admin/list/");
    }



}

package com.mysite.askAnything.admin;

import com.mysite.askAnything.DataNotFoundException;
import com.mysite.askAnything.user.SiteUser;
import com.mysite.askAnything.user.UserRepository;
import com.mysite.askAnything.user.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public SiteUser create(String username, String email, String password, UserRole role){
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(UserRole.GUEST);
        user.setCreateDate(LocalDateTime.now());
        this.adminRepository.save(user);
        return user;
    }


    public void modify(SiteUser siteUser,String email, UserRole role){

        siteUser.setEmail(email);
        /*siteUser.setPassword(passwordEncoder.encode(password));*/
        siteUser.setRole(role);
        this.adminRepository.save(siteUser);
    }




    public SiteUser getUser(String username){
        Optional<SiteUser> siteUser = this.adminRepository.findByusername(username);
        if (siteUser.isPresent()){
            return siteUser.get();
        }else{
            throw new DataNotFoundException("siteuser not found");
        }
    }


    public SiteUser getmodUser(long id){
        Optional<SiteUser> siteUser = this.adminRepository.findByid(id);
        if(siteUser.isPresent()){
            return siteUser.get();
        }else{
            throw new DataNotFoundException("user not found");
        }
    }


    public SiteUser getPwd(String password){
        Optional<SiteUser> siteUser = this.adminRepository.findBypassword(password);
        if(siteUser.isPresent()){
            return siteUser.get();
        }else{
            throw new DataNotFoundException("password not found");
        }
    }

    public SiteUser getEmail(String email){
        Optional<SiteUser> siteUser = this.adminRepository.findByemail(email);
        if(siteUser.isPresent()){
            return siteUser.get();
        }else{
            throw new DataNotFoundException("email not found");
        }
    }

    public SiteUser getRole(UserRole role){
        Optional<SiteUser> siteUser = this.adminRepository.findByrole(role);
        if(siteUser.isPresent()){
            return siteUser.get();
        }else{
            throw new DataNotFoundException("role not identified");
        }
    }

    public Page<SiteUser> getList(int page){
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 8, Sort.by(sorts));
        return this.adminRepository.findAll(pageable);
    }

    public void delete(long id){
        this.adminRepository.deleteById(id);
    }

}

package com.mysite.askAnything.user;

import com.mysite.askAnything.DataNotFoundException;
import com.mysite.askAnything.comment.Comment;
import com.mysite.askAnything.post.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SiteUser create(String username, String email, String password, UserRole role){
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(UserRole.GUEST);
        user.setCreateDate(LocalDateTime.now());
        this.userRepository.save(user);
        return user;
    }

    public void modify(SiteUser siteUser, String email, String password){
        //siteUser.setEmail(email);
        siteUser.setPassword(passwordEncoder.encode(password));
        this.userRepository.save(siteUser);
    }

    public SiteUser getUser(String username){
        Optional<SiteUser> siteUser = this.userRepository.findByusername(username);
        if (siteUser.isPresent()){
            return siteUser.get();
        }else{
            throw new DataNotFoundException("siteuser not found");
        }
    }


    public SiteUser getmodUser(long id){
        Optional<SiteUser> siteUser = this.userRepository.findByid(id);
        if(siteUser.isPresent()){
            return siteUser.get();
        }else{
            throw new DataNotFoundException("user not found");
        }
    }

    public SiteUser getPwd(String password){
        Optional<SiteUser> siteUser = this.userRepository.findBypassword(password);
        if(siteUser.isPresent()){
            return siteUser.get();
        }else{
            throw new DataNotFoundException("password not found");
        }
    }

    public SiteUser getEmail(String email){
        Optional<SiteUser> siteUser = this.userRepository.findByemail(email);
            if(siteUser.isPresent()){
                return siteUser.get();
            }else{
                throw new DataNotFoundException("email not found");
            }
        }

    public SiteUser getRole(UserRole role){
        Optional<SiteUser> siteUser = this.userRepository.findByrole(role);
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
        return this.userRepository.findAll(pageable);
    }




}

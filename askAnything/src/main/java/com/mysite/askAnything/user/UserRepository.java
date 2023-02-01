package com.mysite.askAnything.user;

import com.mysite.askAnything.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<SiteUser, Long> {
    Optional<SiteUser> findByusername(String username);
    Optional<SiteUser> findBypassword(String password);

    Optional<SiteUser> findByrole(UserRole role);

    Optional<SiteUser> findByemail(String email);

    Optional<SiteUser> findByid(long id);
    Page<SiteUser> findAll(Pageable pageable);
    System.out.println("Ddddd");

}

package com.mysite.askAnything.admin;

import com.mysite.askAnything.user.SiteUser;
import com.mysite.askAnything.user.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<SiteUser,Long> {
    Optional<SiteUser> findByid(long id);
    Optional<SiteUser> findBypassword(String password);
Optional<SiteUser> findByusername(String username);
    Optional<SiteUser> findByrole(UserRole role);

    Optional<SiteUser> findByemail(String email);
    
    Page<SiteUser> findAll(Pageable pageable);

    Optional<SiteUser> deleteById(long id);
}

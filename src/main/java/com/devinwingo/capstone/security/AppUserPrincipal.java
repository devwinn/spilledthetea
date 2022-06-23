package com.devinwingo.capstone.security;

import com.devinwingo.capstone.models.AuthGroup;
import com.devinwingo.capstone.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//Security Step 3.1
public class AppUserPrincipal implements UserDetails {

    private User user;
    private List<AuthGroup> authGroup;

    public AppUserPrincipal(User user, List<AuthGroup> authGroup) {
        this.user = user;
        this.authGroup = authGroup;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {


        Set<SimpleGrantedAuthority> authGroupSet = new HashSet<>();
        authGroup.forEach(auth -> authGroupSet.add(new SimpleGrantedAuthority(auth.getRole())));


        return authGroupSet;
    }

    @Override
    public String getPassword() {

        return user.getPassword();
    }

    @Override
    public String getUsername() {

        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

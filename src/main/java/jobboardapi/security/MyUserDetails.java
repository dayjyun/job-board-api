package jobboardapi.security;

import jobboardapi.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;

public class MyUserDetails implements UserDetails {

    // create instance of user
    // private means not accessible by other classes
    // final means user value cannot be changed
    private final User user;

    // constructor
    public MyUserDetails(User user){
        this.user = user;
    }

    // getter only, since we're not setting the user
    public User getUser(){
        return user;
    }



    // methods implemented from UserDetails interface
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new HashSet<>();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
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

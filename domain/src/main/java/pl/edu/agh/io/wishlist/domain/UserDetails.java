package pl.edu.agh.io.wishlist.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

public class UserDetails {

    @Id
    private String id;
    @Indexed(unique = true)
    private String username;
    private String password;
    private String email;
    private List<GrantedAuthority> roles;

    public UserDetails() {}

    public UserDetails(String username, String password, String email){
        this.username = username;
        this.password = password;
        this.email = email;
        this.roles = new ArrayList<>();
    }

    public String getUsername(){
        return this.username;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRoles(List<GrantedAuthority> roles){
        this.roles = roles;
    }

    public List<GrantedAuthority> getRoles(){
        return this.roles;
    }

    public String getPassword() {
        return password;
    }

    public String getId() {
        return id;
    }

    public void addRole(String role){
        this.roles.addAll(AuthorityUtils.createAuthorityList(role));
    }
}

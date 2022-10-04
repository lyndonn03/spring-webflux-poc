package io.lpamintuan.springwebfluxmongo.models;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Document
@Data
@AllArgsConstructor
@Builder
public class UserAccount implements UserDetails {

    @Id
    private String id;

    @NotEmpty(message = "Username must not be empty.")
    @Indexed(unique = true)
    private String username;

    @NotEmpty(message = "Password must not be empty.")
    @Size(min = 8, message = "Password must be atleast 8 characters long.")
    @JsonProperty(access = Access.WRITE_ONLY)
    private String password;

    @Getter(value = AccessLevel.NONE)
    private Boolean isAccountNonExpired;

    @Getter(value = AccessLevel.NONE)
    private Boolean isAccountNonLocked;

    @Getter(value = AccessLevel.NONE)
    private Boolean isCredentialsNonExpired;

    @Getter(value = AccessLevel.NONE)
    private Boolean isEnabled;

    private Set<GrantedAuthority> authorities;

    public UserAccount(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserAccount() {
        this.isAccountNonExpired = true;
        this.isCredentialsNonExpired = true;
        this.isAccountNonLocked = true;
        this.isEnabled = true;
        this.authorities = new HashSet<>();
        this.authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @JsonGetter(value = "isAccountNonExpired")
    @Override
    public boolean isAccountNonExpired() {
        return this.isAccountNonExpired;
    }

    @JsonGetter(value = "isAccountNonLocked")
    @Override
    public boolean isAccountNonLocked() {
        return this.isAccountNonLocked;
    }

    @JsonGetter(value = "isCredentialsNonExpired")
    @Override
    public boolean isCredentialsNonExpired() {
        return this.isCredentialsNonExpired;
    }

    @JsonGetter(value = "isEnabled")
    @Override
    public boolean isEnabled() {
        return true;
    }
    
}

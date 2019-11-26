package com.javamentors.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="role")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="role_id")
    private Long id;

    @Column(name="role")
    private String role;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
    @JsonIgnore
    private Set<AppUser> appUsers = new HashSet<>();



    public Set<AppUser> getAppUsers() {
        return appUsers;
    }

    public void setAppUsers(Set<AppUser> appUsers) {
        this.appUsers = appUsers;
    }

    public Role(){
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return this.role;
    }

    @PreRemove
    private void removeRoleFromUser(){
        for (AppUser appUser : appUsers){
            appUser.getRoles().remove(this);
        }
    }

    @Override
    public String toString() {
        return role ;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Role role1 = (Role) object;
        return id.equals(role1.id) &&
                Objects.equals(role, role1.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, role);
    }
}

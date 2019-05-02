package com.pj.springsecurity.model.user;

import com.pj.springsecurity.model.AbstractAuditingEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Collection;


@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "`user`")
public class User extends AbstractAuditingEntity
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "credentials_non_expired")
    private Boolean credentialsNonExpired;

    @Column(name = "account_non_locked")
    private Boolean accountNonLocked;

    @Column(name = "account_non_expired")
    private Boolean accountNonExpired;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "user")
    @JoinColumn(name = "user_profile_id")
    private UserProfile userProfile;

    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Collection<Role> roles;

    public User()
    {

    }

    public User(String username, String password, Boolean active, UserProfile userProfile)
    {
        this.username = username;
        this.password = password;
        this.active = active;
        this.userProfile = userProfile;
    }

    @Override
    public String toString()
    {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", active=" + active +
                ", password='" + password + '\'' +
                '}';
    }
}
package com.egs.shop.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users", indexes = @Index(columnList = "username"))
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = -5603661293247684856L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", length = 50, unique = true, nullable = false)
    private String username;

    @Column(name = "password_hash", length = 60, nullable = false)
    private String password;

    @Column(name = "email", length = 254, unique = true, nullable = false)
    private String email;

    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;

    @Column(name = "blocked", nullable = false)
    private boolean blocked;

    @Column(name = "activated", nullable = false)
    private boolean activated;

    @Column(name = "activation_key", length = 20)
    private String activationKey;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
    private Profile profile;

    @ManyToMany
    @JoinTable(
            name = "user_authority",
            joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "role_name", referencedColumnName = "name") }
    )
    private Set<Authority> authorities = new HashSet<>();

}

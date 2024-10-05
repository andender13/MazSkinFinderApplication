package andender13.mazskinfinderapplication.entity;

import andender13.mazskinfinderapplication.enums.AuthorizationStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "weapon")
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = true)
    private String emailId;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Column(nullable = true)
    private String telegram;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AuthorizationStatus status = AuthorizationStatus.UNAUTHORIZED;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Weapon> weapon;

    public User(String username, String password, String email) {
        this.password = password;
        this.email = email;
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Здесь можно вернуть роли пользователя, если они есть
        return List.of(() -> "ROLE_USER", () -> "ROLE_ADMIN");
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

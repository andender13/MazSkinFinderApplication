package andender13.mazskinfinderapplication.entity;

import andender13.mazskinfinderapplication.enums.AuthorizationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "weapon")
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

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
}

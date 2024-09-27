package andender13.mazskinfinderapplication.entity;

import andender13.mazskinfinderapplication.enums.StatTrack;
import andender13.mazskinfinderapplication.enums.WeaponQuality;
import andender13.mazskinfinderapplication.model.WeaponFloat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@ToString(exclude = "user")
@Entity
@Getter
@Setter
@Table(name = "weapons")
@AllArgsConstructor
@NoArgsConstructor
public class Weapon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private StatTrack statTrack;

    @Enumerated(EnumType.STRING)
    private WeaponQuality weaponQuality;

    @Embedded
    private WeaponFloat weaponFloat;

    @Column(nullable = false)
    private LocalDateTime searchStarted;

    private Boolean isCanceled = false;

    @ManyToOne
    @JoinColumn(name = "skin_id", nullable = false)
    private Skin skin;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}

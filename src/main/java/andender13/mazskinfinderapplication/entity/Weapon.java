package andender13.mazskinfinderapplication.entity;


import andender13.mazskinfinderapplication.enums.StatTrack;
import andender13.mazskinfinderapplication.enums.WeaponQuality;
import andender13.mazskinfinderapplication.enums.WeaponType;
import andender13.mazskinfinderapplication.model.WeaponFloat;
import jakarta.persistence.*;
import lombok.*;

@ToString
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
    private String skinName;
    @Enumerated(EnumType.STRING)
    private StatTrack statTrack;
    @Enumerated(EnumType.STRING)
    private WeaponQuality weaponQuality;
    @Enumerated(EnumType.STRING)
    private WeaponType weaponType;
    @Embedded
    private WeaponFloat weaponFloat;
}

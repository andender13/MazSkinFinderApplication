package andender13.mazskinfinderapplication.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@ToString
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "skins")
public class Skin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String gunType;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, length = 2048)
    private String imageUrl;

    @OneToMany(mappedBy = "skin", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Weapon> weapons;
}

package andender13.mazskinfinderapplication.service;

import andender13.mazskinfinderapplication.entity.Weapon;
import andender13.mazskinfinderapplication.repo.WeaponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WeaponService {
    @Autowired
    WeaponRepository weaponRepository;

    public List<Weapon> getAllWeapons() {
        return weaponRepository.findAll();
    }

    public Weapon getWeaponById(Long id) {
        ;
        return weaponRepository.findById(id).orElse(null);
    }
}

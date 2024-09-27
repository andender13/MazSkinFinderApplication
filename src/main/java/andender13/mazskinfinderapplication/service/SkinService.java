package andender13.mazskinfinderapplication.service;

import andender13.mazskinfinderapplication.entity.Skin;
import andender13.mazskinfinderapplication.repo.SkinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkinService {
    @Autowired
    private SkinRepository skinRepository;

    public void saveSkin(Skin skin) {
        skinRepository.save(skin);
    }

    public List<String> getAllGunTypes() {
        return skinRepository.findDistinctGunTypes();
    }

    public List<String> getAllSkinNamesByType(String type) {
        return skinRepository.findSkinNamesByGunType(type);
    }

    public Skin getSkinByGunTypeAndName(String gunType, String name) {
        return skinRepository.findByGunTypeAndName(gunType, name).orElse(null);
    }
}

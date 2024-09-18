package andender13.mazskinfinderapplication.controller;

import andender13.mazskinfinderapplication.entity.Weapon;
import andender13.mazskinfinderapplication.enums.WeaponType;
import andender13.mazskinfinderapplication.seleniumSearch.TradeItGGSearch;
import andender13.mazskinfinderapplication.service.WeaponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/skins")
public class SkinController {
    @Autowired
    private WeaponService weaponService;
    private final TradeItGGSearch tradeItGGSearch;

    public SkinController(TradeItGGSearch tradeItGGSearch, WeaponService weaponService) {
        this.tradeItGGSearch = tradeItGGSearch;
        this.weaponService = weaponService;
    }

    @PostMapping("/startSearch")
    public ResponseEntity<?> startSearch(@ModelAttribute("weapon") Weapon weapon, Model model) {
        tradeItGGSearch.startSearch(weapon);
        return ResponseEntity.ok("Search successfully started");
    }

    @GetMapping("/skinNames")
    public ResponseEntity<Map<String, List<String>>> getWeaponSkins() {
        Map<String, List<String>> weaponSkins = new HashMap<>();
        for (WeaponType type : WeaponType.values()) {
            weaponSkins.put(type.getStringType(), type.getSkinNames());
        }
        return ResponseEntity.ok(weaponSkins);
    }

}

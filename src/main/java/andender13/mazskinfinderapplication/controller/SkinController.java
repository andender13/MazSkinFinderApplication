package andender13.mazskinfinderapplication.controller;

import andender13.mazskinfinderapplication.entity.Skin;
import andender13.mazskinfinderapplication.entity.Weapon;
import andender13.mazskinfinderapplication.seleniumSearch.TradeItGGSearch;
import andender13.mazskinfinderapplication.service.SkinService;
import andender13.mazskinfinderapplication.service.WeaponService;
import andender13.mazskinfinderapplication.utility.skinParser.JSoupCsMoneyWikiParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/skins")
public class SkinController {
    @Autowired
    private WeaponService weaponService;
    private final TradeItGGSearch tradeItGGSearch;
    @Autowired
    private JSoupCsMoneyWikiParser jsoupCsMoneyWikiParser;
    @Autowired
    private SkinService skinService;

    public SkinController(TradeItGGSearch tradeItGGSearch, WeaponService weaponService) throws IOException {
        this.tradeItGGSearch = tradeItGGSearch;
        this.weaponService = weaponService;
    }

    @PostMapping("/startSearch")
    public ResponseEntity<?> startSearch(@ModelAttribute("weapon") Weapon weapon) {
        tradeItGGSearch.startSearch(weapon);
        return ResponseEntity.ok("Search successfully started");
    }

    @GetMapping("/skinNames/{gunType}")
    public ResponseEntity<List<String>> getWeaponSkins(@PathVariable("gunType") String gunType) {
        List<String> names = skinService.getAllSkinNamesByType(gunType);
        return ResponseEntity.ok(names);
    }

    @GetMapping("/updateSkins")
    public ResponseEntity<?> updateSkins() {
        try {
            jsoupCsMoneyWikiParser.parseCsMoneyWiki();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok("Skin successfully updated");
    }
}
